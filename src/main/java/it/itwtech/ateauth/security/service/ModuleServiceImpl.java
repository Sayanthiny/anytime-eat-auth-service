package it.itwtech.ateauth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.repositories.ModuleRepository;


@Service
public class ModuleServiceImpl implements ModuleService {
  @Autowired
  private ModuleRepository moduleRepository;

  @Transactional(readOnly = true)
  public boolean isModuleExist(Long moduleId) {
    return moduleRepository.existsById(moduleId);
  }

  @Transactional(readOnly = true)
  public Module getModuleById(Long moduleId) {
    return moduleRepository.getById(moduleId);
  }


}
