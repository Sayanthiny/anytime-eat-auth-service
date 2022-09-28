package it.itwtech.ateauth.service;

import java.util.List;
import org.springframework.http.ResponseEntity;

import it.itwtech.ateauth.dto.RolePermissionDto;
import it.itwtech.ateauth.dto.RolePermissionResponsesDto;
import it.itwtech.ateauth.model.PermissionAllocate;
import it.itwtech.ateauth.model.RolePermission;

public interface RolePermissionService {

  public boolean isRoleIdExists(Long id);

  boolean isModuleExist(Long roleId, Long moduleId);

  public RolePermission createRolePermission(PermissionAllocate permissionAllocate, Long roleId);

  public RolePermission updateRolePermission(PermissionAllocate permissionAllocate, Long roleId);

  boolean isRolePermissionExists(Long id);

  boolean isRolePermissionContainsExists(Long id);

  List<RolePermission> getByRoleId(Long roleId);

  List<RolePermissionResponsesDto> getRolePermissionByRoleId(Long roleId);

  public ResponseEntity<Object> rolePermissionUpdate(RolePermissionDto rolePermissionDto);
  
  public ResponseEntity<Object> rolePermissionCreate(RolePermissionDto rolePermissionDto);
}
