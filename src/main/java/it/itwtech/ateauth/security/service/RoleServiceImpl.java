package it.itwtech.ateauth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Transactional(readOnly = true)
  public boolean isRoleExist(Long roleId) {
    return roleRepository.existsById(roleId);
  }


}
