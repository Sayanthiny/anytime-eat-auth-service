package it.itwtech.ateauth.service;

import it.itwtech.ateauth.model.Restaurant;

public interface RestaurantService {
	  public boolean isRestaurantNameExists(String restaurantName);

	  public boolean isRestaurantMailExists(String restaurantEmail);

	  public void save(Restaurant restaurant);

	  public Restaurant getByRestaurantEmail(String restaurantEmail);

	  public boolean isRestaurantExist(Long id);
}
