package it.itwtech.ateauth.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportErrorResponseDto {

  Set<String> deliveryBoyNIC;
  Set<String> deliveryBoyDrivingLicenceNo;
  Set<String> deliveryBoyPhoneNo;
  Set<String> deliveryBoyEmail;
  Set<String> deliveryBoyBranchNameSet;
  Set<String> deliveryBoyRestaurantNameSet;

}
