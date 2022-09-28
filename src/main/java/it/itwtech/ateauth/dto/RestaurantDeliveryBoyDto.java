package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDeliveryBoyDto {
	 @NotNull(message = "{restaurantDeliveryBoy.firstName.null}")
	  @NotBlank(message = "{restaurantDeliveryBoy.firstName.blank}")
	  @NotEmpty(message = "{restaurantDeliveryBoy.firstName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String firstName;
	  @NotNull(message = "{restaurantDeliveryBoy.lastName.null}")
	  @NotBlank(message = "{restaurantDeliveryBoy.lastName.blank}")
	  @NotEmpty(message = "{restaurantDeliveryBoy.lastName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String lastName;
	  @NotNull(message = "{restaurantDeliveryBoy.nic.null}")
	  @NotBlank(message = "{restaurantDeliveryBoy.nic.blank}")
	  @NotEmpty(message = "{restaurantDeliveryBoy.nic.empty}")
	  @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$", message = "Invalid NIC")
	  private String nic;
	  @NotNull(message = "{restaurantDeliveryBoy.drivingLicenseNo.null}")
	  @NotBlank(message = "{restaurantDeliveryBoy.drivingLicenseNo.blank}")
	  @NotEmpty(message = "{restaurantDeliveryBoy.drivingLicenseNo.empty}")
	  @Pattern(regexp = "^([A-Z]{1}[0-9]{7}[A-Z]{1})|([A-Z]{1}[0-9]{7})$",
	      message = "Invalid Driving License No")
	  private String drivingLicenseNo;
	  private String drivingLicenceType;
	  @NotNull(message = "{restaurantDeliveryBoy.phoneNo.null}")
	  @NotBlank(message = "{restaurantDeliveryBoy.phoneNo.blank}")
	  @NotEmpty(message = "{restaurantDeliveryBoy.phoneNo.empty}")
	  @Pattern(
	      regexp = "^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
	      message = "Invalid Mobile Number")
	  private String phoneNo;
	  private Long branchId;
	  private Long restaurantId;

}
