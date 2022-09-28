package it.itwtech.ateauth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.dto.RolePermissionDto;
import it.itwtech.ateauth.dto.RolePermissionResponsesDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.model.PermissionAllocate;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.RolePermission;
import it.itwtech.ateauth.repositories.ModuleRepository;
import it.itwtech.ateauth.repositories.RolePermissionRepository;
import it.itwtech.ateauth.repositories.RoleRepository;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.security.service.ModuleService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;
import it.itwtech.ateauth.model.Module;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

  @Autowired
  private RolePermissionRepository rolePermissionRepositroy;
  @Autowired
  private ModuleService moduleService;

  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private ModuleRepository moduleRepository;
  private static final Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);
  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;

  @Transactional
  public boolean isRoleIdExists(Long id) {
    return rolePermissionRepositroy.existsById(id);
  }

  @Transactional(readOnly = true)
  public boolean isModuleExist(Long roleId, Long moduleId) {
    return rolePermissionRepositroy.existsByRoleIdAndModuleId(roleId, moduleId);
  }

  @Transactional
  public RolePermission createRolePermission(PermissionAllocate permissionAllocate, Long roleId) {
    RolePermission rolePermission = new RolePermission();
    rolePermission.setStatus(permissionAllocate.isActive());
    Role role = roleRepository.findById(roleId).get();
    rolePermission.setRole(role);
    rolePermission.setPermission(permissionAllocate.getPermission());
    Module module=moduleRepository.findById(permissionAllocate.getModuleId()).get();
    rolePermission.setModule(module);
    return rolePermissionRepositroy.save(rolePermission);
  }

  @Transactional(readOnly = true)
  public boolean isRolePermissionExists(Long id) {
    return rolePermissionRepositroy.existsById(id);
  }

  @Override
  public boolean isRolePermissionContainsExists(Long id) {
    return rolePermissionRepositroy.existsByIdAndContainsTrue(id);
  }

  @Override
  public RolePermission updateRolePermission(PermissionAllocate permissionAllocate, Long roleId) {
    RolePermission rolePermission =
        rolePermissionRepositroy.findById(permissionAllocate.getId()).get();
    rolePermission.setStatus(permissionAllocate.isActive());
    Role role = roleRepository.findById(roleId).get();
    rolePermission.setRole(role);
    rolePermission.setPermission(permissionAllocate.getPermission());
    Module module = moduleRepository.findById(permissionAllocate.getModuleId()).get();
    rolePermission.setModule(module);

    return rolePermissionRepositroy.save(rolePermission);
  }

  @Transactional(readOnly = true)
  public List<RolePermission> getByRoleId(Long roleId) {
    return rolePermissionRepositroy.findAllByRoleId(roleId, Sort.by("id").ascending());
  }

  @Override
  public List<RolePermissionResponsesDto> getRolePermissionByRoleId(Long roleId) {
    List<RolePermission> rolePermissions = getByRoleId(roleId);
    List<RolePermissionResponsesDto> rolePermissionResponsesDto =
        new ArrayList<RolePermissionResponsesDto>();
    rolePermissions.forEach(rolePermission -> {
      RolePermissionResponsesDto permissionResponseDto = new RolePermissionResponsesDto();
      BeanUtils.copyProperties(rolePermission, permissionResponseDto);
      permissionResponseDto.setModuleId(rolePermission.getModule().getId());
      permissionResponseDto.setModuleName(rolePermission.getModule().getName());
      permissionResponseDto.setPermission(rolePermission.getPermission());
      permissionResponseDto.setRoleId(rolePermission.getRole().getId());
      permissionResponseDto.setRoleName(rolePermission.getRole().getRoleName());
      permissionResponseDto.setPermissionName(rolePermission.getModule().getPermissionName());
      rolePermissionResponsesDto.add(permissionResponseDto);
    });
    return rolePermissionResponsesDto;
  }

  @Override
  public ResponseEntity<Object> rolePermissionUpdate(RolePermissionDto rolePermissionDto) {
    Set<Long> rolePermissionNotExists = new HashSet<Long>();
    Set<Long> moduleNotExists = new HashSet<Long>();
    Set<String> moduleAlreadyExists = new HashSet<String>();
    for (PermissionAllocate permissionAllocate : rolePermissionDto.getPermissionAllocations()) {
      if (!isRolePermissionExists(permissionAllocate.getId())) {
        logger.info("Update allocate permissions id not exists, id: '{}'",
            permissionAllocate.getId());
        rolePermissionNotExists.add(permissionAllocate.getId());
        continue;
      }
      if (!isRolePermissionContainsExists(permissionAllocate.getId())) {
        logger.info("Update allocate permissions not Contains, id: '{}'",
            permissionAllocate.getId());
        rolePermissionNotExists.add(permissionAllocate.getId());
        continue;
      }
      if (!moduleService.isModuleExist(permissionAllocate.getModuleId())) {
        logger.info("Add allocate permission module id not exists, module id: '{}'",
            permissionAllocate.getModuleId());
        moduleNotExists.add(permissionAllocate.getModuleId());
        continue;
      }
      if (isModuleExist(rolePermissionDto.getRoleId(), permissionAllocate.getModuleId())) {
        Module module = moduleService.getModuleById(permissionAllocate.getModuleId());
        logger.info("Add allocate permission module id Already exists: module id '{}', role id '"
            + rolePermissionDto.getRoleId() + "'", permissionAllocate.getModuleId());
        moduleAlreadyExists.add(module.getName());
        continue;
      }
      updateRolePermission(permissionAllocate, rolePermissionDto.getRoleId());
      logger.info("Add allocate permission for module:roleId '" + rolePermissionDto.getRoleId()
          + "', moduleID '" + permissionAllocate.getModuleId() + "'");
    }
    if (!moduleNotExists.isEmpty()) {
      logger.debug("Some module not exists: addAllocatePermissions(), moduleNotExists: {}",
          moduleNotExists);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MODULE_NOT_EXITS,
          validationFailureStatusCodes.getModuleNotExists()), HttpStatus.BAD_REQUEST);
    }
    if (!moduleAlreadyExists.isEmpty()) {
      logger.debug("Some module Aleady exists: addAllocatePermissions(), moduleAleadyExists: {}",
          moduleAlreadyExists);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MODULE_ALREADY_EXIST,
          validationFailureStatusCodes.getModuleAlreadyExists()), HttpStatus.BAD_REQUEST);
    }
    if (!rolePermissionNotExists.isEmpty()) {
      logger.debug(
          "Some Permission Allocation not exists: updateAllocatePermissions(), moduleAleadyExists: {}",
          rolePermissionNotExists);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.ROLE_PERMISSION_NOT_EXIST,
          validationFailureStatusCodes.getRolePermissionNotExists()), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.UPDATE_ROLEPERMISSION),
        HttpStatus.OK);
  }

  public ResponseEntity<Object> rolePermissionCreate(RolePermissionDto rolePermissionDto) {
    Set<Long> moduleNotExists = new HashSet<Long>();
    Set<String> moduleAlreadyExists = new HashSet<String>();
    for (PermissionAllocate permissionAllocate : rolePermissionDto.getPermissionAllocations()) {
      if (!moduleService.isModuleExist(permissionAllocate.getModuleId())) {
        logger.info("Add allocate permission module id not exists, module id: '{}'",
            permissionAllocate.getModuleId());
        moduleNotExists.add(permissionAllocate.getModuleId());
        continue;
      }
      if (isModuleExist(rolePermissionDto.getRoleId(), permissionAllocate.getModuleId())) {
        Module module = moduleService.getModuleById(permissionAllocate.getModuleId());
        logger.info("Add allocate permission module id Already exists: module id '{}', role id '"
            + rolePermissionDto.getRoleId() + "'", permissionAllocate.getModuleId());
        moduleAlreadyExists.add(module.getName());
        continue;
      }
      createRolePermission(permissionAllocate, rolePermissionDto.getRoleId());
      logger.info("Add allocate permission for module:roleId '" + rolePermissionDto.getRoleId()
          + "', moduleID '" + permissionAllocate.getModuleId() + "'");
    }
    if (!moduleNotExists.isEmpty()) {
      logger.debug("Some module not exists: addAllocatePermissions(), moduleNotExists: {}",
          moduleNotExists);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MODULE_NOT_EXITS,
          validationFailureStatusCodes.getModuleNotExists()), HttpStatus.BAD_REQUEST);
    }
    if (!moduleAlreadyExists.isEmpty()) {
      logger.debug("Some module Aleady exists: addAllocatePermissions(), moduleAleadyExists: {}",
          moduleAlreadyExists);
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MODULE_ALREADY_EXIST,
          validationFailureStatusCodes.getModuleAlreadyExists()), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.ADD_ROLEPERMISSION), HttpStatus.OK);
  }
}
