package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForgetPasswordDto {

	private Long id;
	private Boolean active;
	private String password;
	private Boolean status;
	private String token;
	private String email;
	private Long employeeId;
}
