package goormthonUniv.MJU.server.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

	private String nickname;
    private String password;
    private String role; // "STUDENT" 또는 "EXPERT"
    private String belonging;
	
}
