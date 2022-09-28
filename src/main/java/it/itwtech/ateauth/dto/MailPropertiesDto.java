package it.itwtech.ateauth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailPropertiesDto {
  private long id;
  @NotNull(message = "{mailProperties.host.null}")
  @NotBlank(message = "{mailProperties.host.blank}")
  @NotEmpty(message = "{mailProperties.host.empty}")
  private String host;
  @NotNull(message = "{mailProperties.port.null}")
  private Integer port;
  @NotNull(message = "{mailProperties.username.null}")
  @NotBlank(message = "{mailProperties.username.blank}")
  @NotEmpty(message = "{mailProperties.username.empty}")
  private String username;
  @NotNull(message = "{mailProperties.password.null}")
  @NotBlank(message = "{mailProperties.password.blank}")
  @NotEmpty(message = "{mailProperties.password.empty}")
  private String password;
  @NotNull(message = "{mailProperties.protocol.null}")
  @NotBlank(message = "{mailProperties.protocol.blank}")
  @NotEmpty(message = "{mailProperties.protocol.empty}")
  private String protocol;
  private boolean active;
}
