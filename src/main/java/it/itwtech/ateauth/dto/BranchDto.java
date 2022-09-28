package it.itwtech.ateauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDto {
  private Long id;
  @NotNull(message = "{branch.branchName.null}")
  @NotBlank(message = "{branch.branchName.blank}")
  @NotEmpty(message = "{branch.branchName.empty}")
  private String branchName;
  @NotNull(message = "{branch.address.null}")
  @NotBlank(message = "{branch.address.blank}")
  @NotEmpty(message = "{branch.address.empty}")
  private String address;
  @NotNull(message = "{branch.phoneNumber.null}")
  @NotBlank(message = "{branch.phoneNumber.blank}")
  @NotEmpty(message = "{branch.phoneNumber.empty}")
  @Pattern(
      regexp = "^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
      message = "Invalid Mobile Number")
  private String phoneNumber;
  @Email
  private String email;
  private Long restaurantId;
}
