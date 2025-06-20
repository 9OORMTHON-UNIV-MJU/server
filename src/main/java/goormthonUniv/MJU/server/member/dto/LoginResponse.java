package goormthonUniv.MJU.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {

	private String accessToken;
	private String nickname;
	private String role;
	
}
