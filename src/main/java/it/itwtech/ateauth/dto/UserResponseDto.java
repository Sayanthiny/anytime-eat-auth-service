package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

  private boolean status;
  private String email;
  private String mobileNumber;
  private String lastName;
  private String branchName;
}
