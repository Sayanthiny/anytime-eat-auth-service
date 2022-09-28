package it.itwtech.ateauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.model.RestaurantAdmin;

@Repository
public interface RestaurantAdminRepository extends JpaRepository<RestaurantAdmin, Long> {
	RestaurantAdmin findByUserId(Long id);

}
