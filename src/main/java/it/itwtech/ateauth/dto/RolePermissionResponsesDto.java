package it.itwtech.ateauth.dto;

import it.itwtech.ateauth.enums.Permissions;
import it.itwtech.ateauth.enums.UserRoles;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RolePermissionResponsesDto {
  private Long id;
  private Long moduleId;
  private String moduleName;
  private Permissions permission;
  private boolean active;
  private Long roleId;
  private UserRoles roleName;
  private boolean contains;
  private String permissionName;
}
