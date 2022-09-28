package it.itwtech.ateauth.dto;

import it.itwtech.ateauth.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchAdminUserDto {
  
  private String firstName;
  private String lastName;
  private String nic;
  private String mobileNumber;
  private String subscription;
  private String email;
  private String password;
  private Boolean status;
  private UserType userType;
  private String token;
  private String branchName;
  private Long branchId;
  private Long restaurantId;

}
