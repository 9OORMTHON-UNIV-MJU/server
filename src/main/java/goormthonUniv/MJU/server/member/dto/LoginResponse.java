package goormthonUniv.MJU.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {

	private Long memberId;
	private String nickname;
	private String accessToken;
	
}
