package it.itwtech.ateauth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.itwtech.ateauth.model.DeliveryBoy;

public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Long>{
	
	 boolean existsByRestaurantId(Long restaurantId);

	  boolean existsByBranchId(Long branchId);

	  List<DeliveryBoy> findAllByRestaurantId(Long restaurantId);
	  
	  List<DeliveryBoy> findAllByRestaurantIdAndBranchId(Long restaurantId,Long branchId);

	  DeliveryBoy findByUserId(Long userId);

	  boolean existsByUserId(Long userId);

	  DeliveryBoy findByUserEmail(String email);

}
