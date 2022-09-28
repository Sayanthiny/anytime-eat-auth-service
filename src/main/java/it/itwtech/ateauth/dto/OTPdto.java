package it.itwtech.ateauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPdto {

  @NotNull(message = "{user.otp.null}")
  @NotBlank(message = "{user.otp.blank}")
  @NotEmpty(message = "{user.otp.empty}")
  private String otp;
  @NotNull(message = "{user.email.null}")
  @NotBlank(message = "{user.email.blank}")
  @NotEmpty(message = "{user.email.empty}")
  @Email
  private String email;
}
