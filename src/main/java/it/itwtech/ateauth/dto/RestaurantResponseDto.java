package it.itwtech.ateauth.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import it.itwtech.ateauth.enums.LicenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {

	private String restaurantName;
	  private String address;
	  private String registrationNumber;
	  private String restaurantPhoneNumber;
	  private String restaurantEmail;
	  @Enumerated(EnumType.ORDINAL)
	  private LicenceType licenceType;
}
