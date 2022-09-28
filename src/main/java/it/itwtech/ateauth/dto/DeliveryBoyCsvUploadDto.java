package it.itwtech.ateauth.dto;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeliveryBoyCsvUploadDto {

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
	  @Value("${user.driverBranchName}")
	  private String driverBranchName;
	  @Value("${user.driverRestaurantName}")
	  private String driverRestaurantName;

	  public String[] getHeaders() {
	    String[] headers = {this.firstName, this.lastName, this.nic, this.mobileNumber, this.email,
	        this.drivingLicenseNo, this.drivingLicenseType, this.driverBranchName,
	        this.driverRestaurantName};
	    return headers;
	  }
}
