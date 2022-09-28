package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.itwtech.ateauth.enums.LicenceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESTAURANT")
	@SequenceGenerator(name = "RESTAURANT", sequenceName = "SEQ_FM_RESTAURANT", allocationSize = 1)
	private Long id;
	private String restaurantName;
	private String address;
	private String registrationNumber;
	private String restaurantPhoneNumber;
	private String restaurantEmail;
	@Enumerated(EnumType.ORDINAL)
	private LicenceType licenceType;

}
