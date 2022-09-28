package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResetPasswordDto {

  @NotNull(message = "{user.token.null}")
  @NotBlank(message = "{user.token.blank}")
  @NotEmpty(message = "{user.token.empty}")
  private String token;
  @NotNull(message = "{user.password.null}")
  @NotBlank(message = "{user.password.blank}")
  @NotEmpty(message = "{user.password.empty}")
  private String password;

}
