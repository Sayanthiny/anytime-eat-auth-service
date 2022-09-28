package it.itwtech.ateauth.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantAdmin {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  @ManyToOne
	  @JoinColumn(name = "user_id", nullable = false)
	  private User user;
	  @ManyToOne
	  @JoinColumn(name = "restaurant_id", nullable = false)
	  private Restaurant restaurant;

}
