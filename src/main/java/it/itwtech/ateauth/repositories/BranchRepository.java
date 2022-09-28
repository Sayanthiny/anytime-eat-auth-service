package it.itwtech.ateauth.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

  public List<Branch> findByRestaurantId(Long restaurantId);

  public boolean existsByEmail(String email);

  public Optional<Branch> findByBranchName(String string);

  public boolean existsByPhoneNumber(String phoneNumber);

}
