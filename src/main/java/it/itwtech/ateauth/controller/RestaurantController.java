package it.itwtech.ateauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import it.itwtech.ateauth.service.RestaurantService;
import it.itwtech.ateauth.utils.EndPointURI;

@CrossOrigin(origins = "*")
@RestController
public class RestaurantController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
	@Autowired
	private RestaurantService restaurantService;

	@GetMapping(value = EndPointURI.CHECK_RESTAURANT)
	Boolean checkRestaurantExitsByRestaurantId(@PathVariable Long id) {
		if (restaurantService.isRestaurantExist(id)) {
			logger.info(" Restaurant exists");
			return true;
		} else {
			logger.info(" Restaurant Not exists");
			return false;
		}
	}

}
