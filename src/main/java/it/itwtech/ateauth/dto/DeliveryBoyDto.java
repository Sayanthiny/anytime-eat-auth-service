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
public class DeliveryBoyDto {

	 private Long deliveryBoyId;
	  @NotNull(message = "{deliveryBoy.firstName.null}")
	  @NotBlank(message = "{deliveryBoy.firstName.blank}")
	  @NotEmpty(message = "{deliveryBoy.firstName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String firstName;
	  @NotNull(message = "{deliveryBoy.lastName.null}")
	  @NotBlank(message = "{deliveryBoy.lastName.blank}")
	  @NotEmpty(message = "{deliveryBoy.lastName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String lastName;
	  @NotNull(message = "{deliveryBoy.nic.null}")
	  @NotBlank(message = "{deliveryBoy.nic.blank}")
	  @NotEmpty(message = "{deliveryBoy.nic.empty}")
	  @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$", message = "Invalid NIC")
	  private String nic;
	  @NotNull(message = "{deliveryBoy.mobileNumber.null}")
	  @NotBlank(message = "{deliveryBoy.mobileNumber.blank}")
	  @NotEmpty(message = "{deliveryBoy.mobileNumber.empty}")
	  @Pattern(
	      regexp = "^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
	      message = "No Special Charcter Accepeted")
	  private String mobileNumber;
	  @NotNull(message = "{deliveryBoy.email.null}")
	  @NotBlank(message = "{deliveryBoy.email.blank}")
	  @NotEmpty(message = "{deliveryBoy.email.empty}")
	  @Email
	  private String email;
	  @NotNull(message = "{deliveryBoy.drivingLicenseNo.null}")
	  @NotBlank(message = "{deliveryBoy.drivingLicenseNo.blank}")
	  @NotEmpty(message = "{deliveryBoy.drivingLicenseNo.empty}")
	  @Pattern(regexp = "^([A-Z]{1}[0-9]{7}[A-Z]{1})|([A-Z]{1}[0-9]{7})$",
	      message = "Invalid Driving License No")
	  private String drivingLicenseNo;
	  private Long drivingLicenseTypeId;
	  private UserType userType;
	  @NotNull(message = "{deliveryBoy.subscription.null}")
	  @NotBlank(message = "{deliveryBoy.subscription.blank}")
	  @NotEmpty(message = "{deliveryBoy.subscription.empty}")
	  private String subscription;
	  @NotNull(message = "{deliveryBoy.branchId.null}")
	  private Long branchId;
	  @NotNull(message = "{deliveryBoy.restaurantId.null}")
	  private Long restaurantId;
}
