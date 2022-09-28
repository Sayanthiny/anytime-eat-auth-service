package it.itwtech.ateauth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

import it.itwtech.ateauth.dto.DeliveryBoyAllocateDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.DeliveryBoyResponseDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.model.DeliveryBoy;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.responses.ContentResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.service.BranchService;
import it.itwtech.ateauth.service.DeliveryBoyService;
import it.itwtech.ateauth.service.TextNotificationService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

public class DeliveryBoyController {
	
	@Autowired
	  private DeliveryBoyService deliveryBoyService;
	  @Autowired
	  private ValidationFailureStatusCodes validationFailureStatusCodes;
//	  @Autowired
//	  private DrivingLicenseTypeRepository drivingLicenseTypeRepository;
	  @Autowired
	  private BranchService branchService;
	  @Autowired
	  private TextNotificationService textNotificationService;

	  private static final Logger logger = LoggerFactory.getLogger(DeliveryBoyController.class);

	  @GetMapping(value = EndPointURI.DELIVERYBOY_BY_RESTAURANTID)
	  public ResponseEntity<Object> getAllDeliveryBoyByRestaurantId(@PathVariable Long restaurantId) {

	    if (!deliveryBoyService.isRestaurantIdExists(restaurantId)) {
	      logger.info(" Restaurant Id  Does not exists");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
	          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    List<DeliveryBoy> deliveryBoyList = deliveryBoyService.getAllDeliveryBoysByRestaurantId(restaurantId);
	    List<DeliveryBoyResponseDto> deliveryBoyResponseDtolist = new ArrayList<>();
	    deliveryBoyResponseDtolist = deliveryBoyService.setDeliveryBoyResponseDto(deliveryBoyList);

	    logger.info("get all deliveryBoys By Restaurant id");
	    return new ResponseEntity<Object>(
	        new ContentResponse<>(Constants.DELIVERYBOY, deliveryBoyResponseDtolist, RestApiResponseStatus.OK),
	        HttpStatus.OK);
	  }


	  @GetMapping(value = EndPointURI.DELIVERYBOY_BY_BRANCHID_AND_RESTAURANTID)
	  public ResponseEntity<Object> getAllDeliveryBoyByBranchIdRestaurantId(@PathVariable Long branchId,
	      @PathVariable Long restaurantId) {

	    if (!deliveryBoyService.isRestaurantIdExists(restaurantId)) {
	      logger.info(" Restaurant Id  Does not exists");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
	          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!deliveryBoyService.isBranchIdExists(branchId)) {
	      logger.info(" branch Id  Does not exists");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ID_NOT_EXIST,
	          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
	    }
	    List<DeliveryBoy> deliveryBoyList = deliveryBoyService.getAllDeliveryBoyByRestaurantIdBranchId(restaurantId, branchId);
	    List<DeliveryBoyResponseDto> deliveryBoyResponseDtolist = new ArrayList<>();
	    deliveryBoyResponseDtolist = deliveryBoyService.setDeliveryBoyResponseDto(deliveryBoyList);

	    logger.info("get all deliveryBoys By Restaurant Id Branch Id");
	    return new ResponseEntity<Object>(
	        new ContentResponse<>(Constants.DELIVERYBOY, deliveryBoyResponseDtolist, RestApiResponseStatus.OK),
	        HttpStatus.OK);
	  }

	  @DeleteMapping(value = EndPointURI.DELIVERYBOY_BY_ID)
	  public ResponseEntity<Object> deleteDeliveryBoyById(@PathVariable Long id) {

	    if (!deliveryBoyService.isDeliveryBoyIdExists(id)) {
	      logger.info(" DeliveryBoy Id does not exists");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.DELIVERYBOY_NOT_EXITS,
	          validationFailureStatusCodes.getDeliveryBoyNotExists()), HttpStatus.BAD_REQUEST);
	    }
	    deliveryBoyService.deleteDeliveryBoyById(id);
	    logger.info("Delete deliveryBoy By id Success");
	    return new ResponseEntity<>(
	        new BasicResponse<>(RestApiResponseStatus.OK, Constants.DELIVERYBOY_DELETE_SUCCESS),
	        HttpStatus.OK);
	  }

	  @PutMapping(value = EndPointURI.DELIVERYBOY)
	  public ResponseEntity<Object> updateDeliveryBoy(@Valid @RequestBody DeliveryBoyDto deliveryBoyDto,
	      HttpServletRequest request) {

	    if (!deliveryBoyService.isDeliveryBoyIdExists(deliveryBoyDto.getDeliveryBoyId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.DELIVERYBOY_NOT_EXITS,
	          validationFailureStatusCodes.getDeliveryBoyNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!branchService.isBranchExist(deliveryBoyDto.getBranchId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
	          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
	    }
//	    if (!drivingLicenseTypeRepository.existsById(deliveryBoyDto.getDrivingLicenseTypeId())) {
//	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.DRIVINGLICENSE_NOT_EXITS,
//	          validationFailureStatusCodes.getDrivingLicenseNotExists()), HttpStatus.BAD_REQUEST);
//	    }

	    deliveryBoyService.updateDeliveryBoy(deliveryBoyDto, request);

	    return new ResponseEntity<>(
	        new BasicResponse<>(RestApiResponseStatus.OK, Constants.DELIVERYBOY_UPDATE_SUCCESS),
	        HttpStatus.OK);
	  }

	  @PutMapping(value = EndPointURI.DELIVERYBOY_ALLOCATE)
	  public ResponseEntity<Object> allocateDeliveryBoy(
	      @Valid @RequestBody DeliveryBoyAllocateDto deliveryBoyAllocateDto) {

	    if (!deliveryBoyService.isUserIdExists(deliveryBoyAllocateDto.getUserId())) {
	      logger.info(" DeliveryBoy Id  Does not exists: {deliveryBoyAllocateDto.getUserId()}");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.DELIVERYBOY_NOT_EXITS,
	          validationFailureStatusCodes.getDeliveryBoyNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!branchService.isBranchExist(deliveryBoyAllocateDto.getBranchId())) {
	      logger.info(" Branch Id  Does not exists: {deliveryBoyAllocateDto.getBranchId()}");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
	          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!deliveryBoyService.isRestaurantIdExists(deliveryBoyAllocateDto.getRestaurantId())) {
	      logger.info(" Restaurant Id  Does not exists: {deliveryBoyAllocateDto.getRestaurantId()}");
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
	          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    deliveryBoyService.allocateDeliveryBoyToBranch(deliveryBoyAllocateDto);
	    try {
	      textNotificationService.sendDeliveryBoyAllocationTextNotification(deliveryBoyAllocateDto);
	    } catch (HttpClientErrorException httpClientErrorException) {
	      return new ResponseEntity<>(
	          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
	              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
	          HttpStatus.BAD_REQUEST);
	    }
	    
	    logger.info(" DeliveryBoy allocated success");
	    return new ResponseEntity<>(
	        new BasicResponse<>(RestApiResponseStatus.OK, Constants.DELIVERYBOY_ALLOCATE_SUCCESS),
	        HttpStatus.OK);
	  }

}
