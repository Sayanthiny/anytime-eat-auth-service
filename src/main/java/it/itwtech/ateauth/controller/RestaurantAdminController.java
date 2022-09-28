package it.itwtech.ateauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

import it.itwtech.ateauth.dto.BranchAdminAllocateDto;
import it.itwtech.ateauth.dto.BranchUserDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.responses.ContentResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.service.BranchAdminService;
import it.itwtech.ateauth.service.BranchService;
import it.itwtech.ateauth.service.RestaurantService;
import it.itwtech.ateauth.service.TextNotificationService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

public class RestaurantAdminController {
	
	 @Autowired
	  private BranchAdminService branchAdminService;
	  @Autowired
	  private RestaurantService restaurantService;
	  @Autowired
	  private BranchService branchService;
	  @Autowired
	  private ValidationFailureStatusCodes validationFailureStatusCodes;
	  @Autowired
	  private TextNotificationService textNotificationService;

	  private static final Logger logger = LoggerFactory.getLogger(BranchAdminController.class);

	  @GetMapping(value = EndPointURI.BRANCH_ADMIN_BY_RESTAURANT)
	  public ResponseEntity<Object> getAllBranchAdminWithoutPagination(@PathVariable Long id) {

	    if (!restaurantService.isRestaurantExist(id)) {
	      return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
	          validationFailureStatusCodes.getRestaurantIdNotExists()), HttpStatus.BAD_REQUEST);
	    }
	    logger.info("Get all Branch admin by restaurant id");
	    return new ResponseEntity<>(
	        new ContentResponse<>(Constants.BRANCH_ADMIN,
	            branchAdminService.getAllBranchAdminByRestaurant(id), RestApiResponseStatus.OK),
	        HttpStatus.OK);

	  }


	  @DeleteMapping(value = EndPointURI.BRANCH_ADMIN_BY_ID)
	  public ResponseEntity<Object> deleteBranchAminById(@PathVariable Long id) {
	    if (branchAdminService.isBranchAdminExists(id)) {
	      try {
	        logger.info("Delete Branch Admin By Id");
	        branchAdminService.deleteBranchAdmin(id);
	        return new ResponseEntity<>(
	            new BasicResponse<>(RestApiResponseStatus.OK, Constants.DELETE_BRANCH_ADMIN_SUCCESS),
	            HttpStatus.OK);
	      } catch (DataIntegrityViolationException e) {
	        logger.info("Delete Branch Admin by id {} is Depened", id);
	        return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ADMIN_DEPEND,
	            validationFailureStatusCodes.getBranchAdminDepended()), HttpStatus.BAD_REQUEST);
	      }
	    }
	    logger.info("Delete Branch Admin By Id : id {} Not exists", id);
	    return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ADMIN_NOT_EXITS,
	        validationFailureStatusCodes.getBranchAdminNotExists()), HttpStatus.BAD_REQUEST);
	  }


	  @PutMapping(value = EndPointURI.BRANCH_ADMIN)
	  public ResponseEntity<Object> updateBranchAdmin(@Valid @RequestBody BranchUserDto branchUserDto,
	      HttpServletRequest request) {

	    if (!branchAdminService.isBranchAdminExists(branchUserDto.getBranchAdminId())) {
	      logger.info("Delete Branch Admin By Id : id {} Not exists", branchUserDto.getBranchAdminId());
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ADMIN_NOT_EXITS,
	          validationFailureStatusCodes.getBranchAdminNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!branchService.isBranchExist(branchUserDto.getBranchId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
	          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    branchAdminService.updateBranchAdmin(branchUserDto, request);

	    return new ResponseEntity<>(
	        new BasicResponse<>(RestApiResponseStatus.OK, Constants.BRANCH_ADMIN_UPDATE_SUCCESS),
	        HttpStatus.OK);
	  }

	  @GetMapping(value = EndPointURI.BRANCH_ADMIN_BY_BRANCHID)
	  public ResponseEntity<Object> getBranchAdminByBrandId(@PathVariable Long id) {

	    if (!branchService.isBranchExist(id)) {
	      return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	    }
	    logger.info("Get  Branch admin by branch id");
	    return new ResponseEntity<>(branchAdminService.getBranchAdminByBranchId(id), HttpStatus.OK);

	  }

	  @PutMapping(value = EndPointURI.BRANCH_ADMIN_ALLOCATE)
	  public ResponseEntity<Object> allocateBranchAdmin(
	      @Valid @RequestBody BranchAdminAllocateDto branchAdminAllocateDto ) {

	    if (!branchAdminService.isBranchAdminUserExists(branchAdminAllocateDto.getUserId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ADMIN_NOT_EXITS,
	          validationFailureStatusCodes.getBranchAdminNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    if (!branchService.isBranchExist(branchAdminAllocateDto.getBranchId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
	          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
	    }
	    if (!restaurantService.isRestaurantExist(branchAdminAllocateDto.getRestaurantId())) {
	      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
	          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
	    }

	    String oldBranch = branchAdminService.allocateBranchAdmin(branchAdminAllocateDto);
	    try {
	      textNotificationService.sendBranchAdminAllocationTextNotification(oldBranch,
	          branchAdminAllocateDto);
	      
	    } catch (HttpClientErrorException httpClientErrorException) {
	      return new ResponseEntity<>(
	          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
	              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
	          HttpStatus.BAD_REQUEST);
	    }
	    return new ResponseEntity<>(
	        new BasicResponse<>(RestApiResponseStatus.OK, Constants.BRANCH_ADMIN_ALLOCATE_SUCCESS),
	        HttpStatus.OK);
	  }

}
