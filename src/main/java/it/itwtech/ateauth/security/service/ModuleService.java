package it.itwtech.ateauth.security.service;

public interface ModuleService {
  boolean isModuleExist(Long moduleId);

   Module getModuleById(Long moduleId);
}
