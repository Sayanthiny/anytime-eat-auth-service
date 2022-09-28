package it.itwtech.ateauth.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.itwtech.ateauth.model.BranchAdmin;

@Repository
public interface BranchAdminRepository extends JpaRepository<BranchAdmin, Long> {
  public List<BranchAdmin> findByRestaurantId(Long restaurantId);

  public BranchAdmin findByBranchId(Long branchId);

  public BranchAdmin findByUserId(Long userId);

  public boolean existsByUserId(Long id);

  public BranchAdmin findByUserEmail(String email);
}
