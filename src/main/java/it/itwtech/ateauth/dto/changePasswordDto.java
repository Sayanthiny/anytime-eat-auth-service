package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changePasswordDto {
  @NotNull(message = "{user.oldPassword.null}")
  @NotBlank(message = "{user.oldPassword.blank}")
  @NotEmpty(message = "{user.oldPassword.empty}")
  private String oldPassword;
  @NotNull(message = "{user.newPassword.null}")
  @NotBlank(message = "{user.newPassword.blank}")
  @NotEmpty(message = "{user.newPassword.empty}")
  private String newPassword;
}
