package it.itwtech.ateauth.dto;

import java.util.List;

import it.itwtech.ateauth.model.PermissionAllocate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionDto {
  private Long roleId;
  private List<PermissionAllocate> permissionAllocations;
}
