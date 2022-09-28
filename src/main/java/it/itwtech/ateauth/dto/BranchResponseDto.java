package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchResponseDto {
  private String branchName;
  private String address;
  private String phoneNumber;
  private String email;
}
