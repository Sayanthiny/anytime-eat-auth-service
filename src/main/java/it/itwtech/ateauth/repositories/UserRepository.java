package it.itwtech.ateauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.enums.UserType;
import it.itwtech.ateauth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);

  public boolean existsByEmail(String mail);

  public boolean existsByFirstName(String firstName);

  public boolean existsByLastName(String lastName);

  public boolean existsByNic(String nic);

  public boolean existsByDrivingLicenseNo(String drivingLicenseNo);

  public boolean existsByDrivingLicenseType(String drivingLicenseType);

  public boolean existsByMobileNumber(String phoneNo);

  public boolean existsByUserType(String userType);

  // public User getById(long id);

  // public boolean existsById(Long id);

  // public User findByToken(String token);
  
  public boolean existsByEmailAndUserType(String mail,UserType userType);
}
