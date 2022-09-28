package it.itwtech.ateauth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.itwtech.ateauth.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	  boolean existsByRestaurantName(String restaurantName);

	  boolean existsByRestaurantEmail(String restaurantEmail);

	  Restaurant findByRestaurantEmail(String restaurantEmail);

	  Optional<Restaurant> findByRestaurantName(String restaurantName);
}
