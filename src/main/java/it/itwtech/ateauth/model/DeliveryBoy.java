package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="deliveryBoy")
public class DeliveryBoy {

	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  @ManyToOne
	  @JoinColumn(name = "branch_id", nullable = false)
	  private Branch branch;
	  @ManyToOne
	  @JoinColumn(name = "restaurant_id", nullable = false)
	  private Restaurant restaurant;
	  @OneToOne
	  @JoinColumn(name = "user_id", nullable = false)
	  private User user;
}
