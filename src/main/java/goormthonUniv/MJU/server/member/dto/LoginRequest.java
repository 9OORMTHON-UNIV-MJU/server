package goormthonUniv.MJU.server.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	private Long memberId;
	private String nickname;
	private String password;
	
}
