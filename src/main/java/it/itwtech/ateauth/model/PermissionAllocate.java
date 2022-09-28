package it.itwtech.ateauth.model;

import it.itwtech.ateauth.enums.Permissions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionAllocate {
  private Long id;
  private Long moduleId;
  private Permissions permission;
  private boolean active;
}
