package it.itwtech.ateauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.itwtech.ateauth.dto.RolePermissionDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.responses.ContentResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.security.service.RoleService;
import it.itwtech.ateauth.service.RolePermissionService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

@CrossOrigin(origins = "*")
@RestController
public class RolePermissionsController {
  @Autowired
  private RoleService roleService;
  private static final Logger logger = LoggerFactory.getLogger(RolePermissionsController.class);
  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;
  @Autowired
  private RolePermissionService rolePermissionService;

  @PostMapping(EndPointURI.ROLE_PERMISSION)
  public ResponseEntity<Object> addRolePermission(
      @RequestBody RolePermissionDto rolePermissionDto) {
    if (!roleService.isRoleExist(rolePermissionDto.getRoleId())) {
      logger.info("Add allocate permissions role id not exists, role id: '{}'",
          rolePermissionDto.getRoleId());
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.ROLE_NOT_EXIST,
          validationFailureStatusCodes.getRoleNotExists()), HttpStatus.BAD_REQUEST);
    }
    return rolePermissionService.rolePermissionCreate(rolePermissionDto);
  }

  @PutMapping(EndPointURI.ROLE_PERMISSION)
  public ResponseEntity<Object> updateRolePermission(
      @RequestBody RolePermissionDto rolePermissionDto) {

    if (!roleService.isRoleExist(rolePermissionDto.getRoleId())) {
      logger.info("Add allocate permissions role id not exists, role id: '{}'",
          rolePermissionDto.getRoleId());
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.ROLE_NOT_EXIST,
          validationFailureStatusCodes.getRoleNotExists()), HttpStatus.BAD_REQUEST);
    }

    return rolePermissionService.rolePermissionUpdate(rolePermissionDto);
  }

  @GetMapping(EndPointURI.ROLE_PERMISSION_BY_ID)
  public ResponseEntity<Object> getRolePermissionByRoleid(@PathVariable Long id) {
    if (!roleService.isRoleExist(id)) {
      logger.info("Add allocate permissions role id not exists, role id: '{}'", id);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.ROLE_NOT_EXIST,
          validationFailureStatusCodes.getRoleNotExists()), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ContentResponse<>(Constants.ROLE_PERMISSION,
            rolePermissionService.getRolePermissionByRoleId(id), RestApiResponseStatus.OK),
        HttpStatus.OK);
  }
}
