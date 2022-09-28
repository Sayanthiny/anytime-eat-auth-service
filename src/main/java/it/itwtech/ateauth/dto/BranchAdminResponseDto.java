package it.itwtech.ateauth.dto;

import it.itwtech.ateauth.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchAdminResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String nic;
  private String mobileNumber;
  private String email;
  private Boolean status;
  private UserType userType;
//  private String drivingLicenseNo;
  private RestaurantResponseDto restaurantResponseDto;
  private BranchResponseDto branchResponseDto;
}
