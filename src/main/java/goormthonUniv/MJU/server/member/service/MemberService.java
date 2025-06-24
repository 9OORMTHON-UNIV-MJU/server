package goormthonUniv.MJU.server.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import goormthonUniv.MJU.server.global.exception.ExceptionCode;
import goormthonUniv.MJU.server.member.dto.LoginRequest;
import goormthonUniv.MJU.server.member.dto.RegisterRequest;
import goormthonUniv.MJU.server.member.entity.Member;
import goormthonUniv.MJU.server.member.exception.CustomLoginException;
import goormthonUniv.MJU.server.member.exception.CustomRegisterException;
import goormthonUniv.MJU.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
 	public void register(RegisterRequest request) {
 		
 		// 닉네임 중복 확인
 		if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
 			throw new CustomRegisterException(ExceptionCode.DUPLICATE_NICKNAME);
 		}
 		
 		// 비밀번호 암호화
 		String encodedPassword = passwordEncoder.encode(request.getPassword());
 		
 		// Member 엔티티 생성 및 저장
         Member newMember = Member.builder()
                 .nickname(request.getNickname())
                 .password(encodedPassword)
                 .role(request.getRole())
                 .belonging(request.getBelonging())
                 .build();

         memberRepository.save(newMember);
 		
 	}
    
    // 로그인 로직
	public Member login(LoginRequest request) {
		
		// 닉네임으로 사용자 조회
		Member member = memberRepository.findByNickname(request.getNickname())
						.orElseThrow(() -> new CustomLoginException(ExceptionCode.MEMBER_NOT_FOUND));

		// 비밀번호 일치 여부 확인
		if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
			throw new CustomLoginException(ExceptionCode.INVALID_PASSWORD);
		}

		return member;
        
	}
	
}
