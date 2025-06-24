package goormthonUniv.MJU.server.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonUniv.MJU.server.global.config.JwtTokenUtil;
import goormthonUniv.MJU.server.member.dto.LoginRequest;
import goormthonUniv.MJU.server.member.dto.LoginResponse;
import goormthonUniv.MJU.server.member.dto.RegisterRequest;
import goormthonUniv.MJU.server.member.entity.Member;
import goormthonUniv.MJU.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
		memberService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Member member = memberService.login(request);
        String token = jwtTokenUtil.generateAccessToken(member.getMemberId(), member.getRole());

        return ResponseEntity.ok(new LoginResponse(member.getMemberId(), member.getNickname(), token));

    }
	
}