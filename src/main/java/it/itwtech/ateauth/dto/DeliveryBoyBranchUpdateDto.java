package it.itwtech.ateauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryBoyBranchUpdateDto {

	@NotNull(message = "{deliveryBoy.email.null}")
	  @NotBlank(message = "{deliveryBoy.email.blank}")
	  @NotEmpty(message = "{deliveryBoy.email.empty}")
	  @Email
	  public String email;
	  @NotNull(message = "{deliveryBoy.branchName.null}")
	  @NotBlank(message = "{deliveryBoy.branchName.blank}")
	  @NotEmpty(message = "{deliveryBoy.branchName.empty}")
	  public String branchName;
}
