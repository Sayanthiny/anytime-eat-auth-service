package it.itwtech.ateauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>  {
  
  public boolean existsByToken(String token);

  public VerificationToken findByToken(String token);

  public VerificationToken findByUserEmail(String email);

  public VerificationToken findByUserId(Long id);

}
