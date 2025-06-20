package goormthonUniv.MJU.server.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonUniv.MJU.server.member.dto.LoginRequest;
import goormthonUniv.MJU.server.member.dto.LoginResponse;
import goormthonUniv.MJU.server.member.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = memberService.login(request);
		return ResponseEntity.ok(response);
	}
	
}
