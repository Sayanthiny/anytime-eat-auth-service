package it.itwtech.ateauth.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.model.DrivingLicenseType;

@Repository
public interface DrivingLicenseTypeRepository extends JpaRepository<DrivingLicenseType, Long> {

  Optional<DrivingLicenseType> findByType(String string);

}
