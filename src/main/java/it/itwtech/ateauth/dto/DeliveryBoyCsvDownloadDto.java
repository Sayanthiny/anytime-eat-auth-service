package it.itwtech.ateauth.dto;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryBoyCsvDownloadDto {
	
	  @Value("${user.firstName}")
	  private String firstName;
	  @Value("${user.lastName}")
	  private String lastName;
	  @Value("${user.nic}")
	  private String nic;
	  @Value("${user.mobileNumber}")
	  private String mobileNumber;
	  @Value("${user.email}")
	  private String email;
	  @Value("${user.drivingLicenseNo}")
	  private String drivingLicenseNo;
	  @Value("${user.drivingLicenseType}")
	  private String drivingLicenseType;

	  public String[] getHeaders() {
	    String[] headers = {this.firstName, this.lastName, this.nic, this.mobileNumber, this.email,
	        this.drivingLicenseNo, this.drivingLicenseType};
	    return headers;
	  }

}
