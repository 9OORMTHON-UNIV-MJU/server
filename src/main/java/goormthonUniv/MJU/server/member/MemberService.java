package goormthonUniv.MJU.server.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import goormthonUniv.MJU.server.global.config.JwtTokenProvider;
import goormthonUniv.MJU.server.global.exception.CustomLoginException;
import goormthonUniv.MJU.server.global.exception.ExceptionCode;
import goormthonUniv.MJU.server.member.dto.LoginRequest;
import goormthonUniv.MJU.server.member.dto.LoginResponse;
import goormthonUniv.MJU.server.member.dto.RegisterRequest;
import goormthonUniv.MJU.server.member.entity.Member;
import goormthonUniv.MJU.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인 로직
	public LoginResponse login(LoginRequest request) {
		
		// 닉네임으로 사용자 조회
		Member member = memberRepository.findByNickname(request.getNickname())
						.orElseThrow(() -> new CustomLoginException(ExceptionCode.MEMBER_NOT_FOUND));

		// 비밀번호 일치 여부 확인
		if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
			throw new CustomLoginException(ExceptionCode.INVALID_PASSWORD);
		}

        // AccessToken 생성
		String accessToken = jwtTokenProvider.generateAccessToken(member.getNickname(), member.getRole());

		// 응답 DTO 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
        
	}
	
}
