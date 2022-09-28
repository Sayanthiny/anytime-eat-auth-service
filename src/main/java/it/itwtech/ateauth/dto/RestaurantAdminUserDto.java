package it.itwtech.ateauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.itwtech.ateauth.enums.LicenceType;
import it.itwtech.ateauth.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantAdminUserDto {
	
	  @NotNull(message = "{restaurant.restaurantName.null}")
	  @NotBlank(message = "{restaurant.restaurantName.blank}")
	  @NotEmpty(message = "{restaurant.restaurantName.empty}")
	  private String restaurantName;
	  @NotNull(message = "{restaurant.address.null}")
	  @NotBlank(message = "{restaurant.address.blank}")
	  @NotEmpty(message = "{restaurant.address.empty}")
	  private String address;
	  private String registrationNumber;
	  @NotNull(message = "{restaurant.restaurantPhoneNumber.null}")
	  @NotBlank(message = "{restaurant.restaurantPhoneNumber.blank}")
	  @NotEmpty(message = "{restaurant.restaurantPhoneNumber.empty}")
	  private String restaurantPhoneNumber;
	  @Email
	  @NotNull(message = "{restaurant.restaurantPhoneNumber.null}")
	  @NotBlank(message = "{restaurant.restaurantPhoneNumber.blank}")
	  @NotEmpty(message = "{restaurant.restaurantPhoneNumber.empty}")
	  private String restaurantEmail;
	  private LicenceType licenceType;
	  
	  @NotNull(message = "{restaurantAdmin.firstName.null}")
	  @NotBlank(message = "{restaurantAdmin.firstName.blank}")
	  @NotEmpty(message = "{restaurantAdmin.firstName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String firstName;
	  @NotNull(message = "{restaurantAdmin.lastName.null}")
	  @NotBlank(message = "{restaurantAdmin.lastName.blank}")
	  @NotEmpty(message = "{restaurantAdmin.lastName.empty}")
	  @Pattern(regexp = "^[a-zA-Z ]*$", message = "No Special Charcter Accepeted")
	  private String lastName;
	  @NotNull(message = "{restaurantAdmin.nic.null}")
	  @NotBlank(message = "{restaurantAdmin.nic.blank}")
	  @NotEmpty(message = "{restaurantAdmin.nic.empty}")
	  @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$", message = "Invalid NIC")
	  private String nic;
	  @NotNull(message = "{restaurantAdmin.mobileNumber.null}")
	  @NotBlank(message = "{restaurantAdmin.mobileNumber.blank}")
	  @NotEmpty(message = "{restaurantAdmin.mobileNumber.empty}")
	  @Pattern(
	      regexp = "^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
	      message = "Invalid Mobile Number")
	  private String mobileNumber;
	  @NotNull(message = "{restaurantAdmin.subscription.null}")
	  @NotBlank(message = "{restaurantAdmin.subscription.blank}")
	  @NotEmpty(message = "{restaurantAdmin.subscription.empty}")
	  private String subscription;
	  @Email
	  @NotNull(message = "{restaurantAdmin.email.null}")
	  @NotBlank(message = "{restaurantAdmin.email.blank}")
	  @NotEmpty(message = "{restaurantAdmin.email.empty}")
	  private String email;
	  private Boolean status;
	  private UserType userType;
	  private String token;

}
