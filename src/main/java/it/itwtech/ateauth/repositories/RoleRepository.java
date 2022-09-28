package it.itwtech.ateauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.enums.UserRoles;
import it.itwtech.ateauth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByRoleName(UserRoles userRoles);

}
