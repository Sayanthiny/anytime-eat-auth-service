package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpResendDto {

  @NotNull(message = "{deliveryBoy.userId.null}")
  private Long userId;

}
