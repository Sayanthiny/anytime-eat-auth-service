package it.itwtech.ateauth.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "restaurant_deliveryBoy")
public class RestaurantDeliveryBoy {

	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  @ManyToOne
	  @JoinColumn(name = "branch_id")
	  private Branch branch;
	  @ManyToOne
	  @JoinColumn(name = "restaurant_id")
	  private Restaurant restaurant;
}
