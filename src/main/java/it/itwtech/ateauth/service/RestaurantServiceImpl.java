package it.itwtech.ateauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.repositories.RestaurantRepository;

public class RestaurantServiceImpl implements RestaurantService{

	@Autowired
	  private RestaurantRepository restaurantRepository;

	  @Override
	  public boolean isRestaurantExist(Long id) {
	    return restaurantRepository.existsById(id);
	  }

	  @Transactional
	  public boolean isRestaurantNameExists(String restaurantName) {
	    return restaurantRepository.existsByRestaurantName(restaurantName);
	  }

	  @Transactional
	  public boolean isRestaurantMailExists(String restaurantEmail) {
	    return restaurantRepository.existsByRestaurantEmail(restaurantEmail);
	  }

	  @Transactional
	  @Async
	  public void save(Restaurant restaurant) {
	    restaurantRepository.save(restaurant);
	  }

	  @Override
	  public Restaurant getByRestaurantEmail(String restaurantEmail) {
	    return restaurantRepository.findByRestaurantEmail(restaurantEmail);
	  }
}
