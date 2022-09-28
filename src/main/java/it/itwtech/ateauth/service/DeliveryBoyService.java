package it.itwtech.ateauth.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import it.itwtech.ateauth.dto.DeliveryBoyAllocateDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.DeliveryBoyResponseDto;
import it.itwtech.ateauth.model.DeliveryBoy;

public interface DeliveryBoyService {

	  public boolean isBranchIdExists(Long branchId);

	  public List<DeliveryBoy> getAllDeliveryBoyByRestaurantIdBranchId(Long restaurantId, Long branchId);

	  public List<DeliveryBoyResponseDto> setDeliveryBoyResponseDto(List<DeliveryBoy> DeliveryBoyList);

	  public boolean isDeliveryBoyIdExists(Long id);

	  public void deleteDeliveryBoyById(Long id);

	  public void updateDeliveryBoy(@Valid DeliveryBoyDto deliveryBoyDto, HttpServletRequest request);

	  public void allocateDeliveryBoyToBranch(@Valid DeliveryBoyAllocateDto deliveryBoyAllocateDto);

	  public boolean isUserIdExists(Long id);

	  public DeliveryBoy findByUserId(Long id);

	  public boolean isRestaurantIdExists(Long restaurantId);

	  public List<DeliveryBoy> getAllDeliveryBoysByRestaurantId(Long restaurantId);
}
