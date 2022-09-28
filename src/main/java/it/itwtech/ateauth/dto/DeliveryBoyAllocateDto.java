package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryBoyAllocateDto {
	 @NotNull(message = "{deliveryBoy.userId.null}")
	  private Long userId;
	  @NotNull(message = "{deliveryBoy.branchId.null}")
	  private Long branchId;
	  @NotNull(message = "{deliveryBoy.companyId.null}")
	  private Long restaurantId;

}
