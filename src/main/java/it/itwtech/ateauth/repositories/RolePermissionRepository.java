package it.itwtech.ateauth.repositories;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.enums.UserRoles;
import it.itwtech.ateauth.model.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

  public List<RolePermission> findAllByRoleIn(Collection<UserRoles> roles);

  public List<RolePermission> findAllByRoleId(Long id);

  boolean existsByRoleIdAndModuleId(Long roleId, Long moduleId);

  boolean existsByIdAndContainsTrue(Long id);

  List<RolePermission> findAllByRoleId(Long roleId, Sort ascending);

}
