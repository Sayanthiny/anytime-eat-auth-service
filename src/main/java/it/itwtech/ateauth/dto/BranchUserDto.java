package it.itwtech.ateauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.itwtech.ateauth.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchUserDto {
  private Long branchAdminId;
  @NotNull(message = "{user.firstName.null}")
  @NotBlank(message = "{user.firstName.blank}")
  @NotEmpty(message = "{user.firstName.empty}")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
  private String firstName;
  @NotNull(message = "{user.lastName.null}")
  @NotBlank(message = "{user.lastName.blank}")
  @NotEmpty(message = "{user.lastName.empty}")
  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
  private String lastName;
  @NotNull(message = "{user.nic.null}")
  @NotBlank(message = "{user.nic.blank}")
  @NotEmpty(message = "{user.nic.empty}")
  @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$", message = "Invalid NIC")
  private String nic;
  @Pattern(
      regexp = "^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
      message = "Invalid Mobile Number")
  private String mobileNumber;
  @Email
  private String email;
//  @Pattern(regexp = "^([A-Z]{1}[0-9]{7}[A-Z]{1})|([A-Z]{1}[0-9]{7})$",
//      message = "Invalid Driving License No")
//  private String drivingLicenseNo;
//  private String drivingLicenseType;
  private UserType userType;
  @NotNull(message = "{user.subscription.null}")
  @NotBlank(message = "{user.subscription.blank}")
  @NotEmpty(message = "{user.subscription.empty}")
  private String subscription;
  private Long branchId;
  private Long RestaurantId;
}
