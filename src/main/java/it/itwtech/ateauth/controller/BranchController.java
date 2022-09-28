package it.itwtech.ateauth.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.itwtech.ateauth.dto.BranchDto;
import it.itwtech.ateauth.dto.CorporateBranchResponseDto;
import it.itwtech.ateauth.dto.CorporateRestaurantResponseDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.responses.ContentResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.service.BranchService;
import it.itwtech.ateauth.service.RestaurantService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

@RestController
public class BranchController {

  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;
  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private BranchService branchService;
  @Autowired
  private BranchRepository branchRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;
  private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

  @GetMapping(value = EndPointURI.CHECK_BRANCH)
  Boolean checkBranchExitsByBranchId(@PathVariable Long id) {
    if (branchService.isBranchExist(id)) {
      logger.info(" Branch exists");
      return true;
    } else {
      logger.info(" Branch not exists");
      return false;
    }
  }

  @PostMapping(value = EndPointURI.BRANCH)
  public ResponseEntity<Object> createBranch(@Valid @RequestBody BranchDto branchDto,
      HttpServletRequest request) {

    if (branchService.isMailExist(branchDto.getEmail())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.EMAIL_ALREADY_EXITS,
          validationFailureStatusCodes.getEmailAlreadyExist()), HttpStatus.BAD_REQUEST);
    }

    if (!restaurantService.isRestaurantExist(branchDto.getRestaurantId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_ID_NOT_EXIST,
          validationFailureStatusCodes.getRestaurantIdNotExists()), HttpStatus.BAD_REQUEST);
    }

    if (branchService.isBranchExist(branchDto.getId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ID_ALREADY_EXIST,
          validationFailureStatusCodes.getBranchAlreadyExists()), HttpStatus.BAD_REQUEST);
    }
    if (branchService.isMobileExists(branchDto.getPhoneNumber())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MOBILE_NO_ALREADY_EXITS,
          validationFailureStatusCodes.getMobileNoAlreadyExit()), HttpStatus.BAD_REQUEST);
    }

    Branch branch = new Branch();
    Restaurant restaurant = new Restaurant();
    restaurant.setId(branchDto.getRestaurantId());
    branch.setRestaurant(restaurant);
    BeanUtils.copyProperties(branchDto, branch);
    Branch branchDetails = branchService.addBranch(branch);
    logger.info("Create branch");
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.CREATE_BRANCH), HttpStatus.OK);
  }

  @PutMapping(value = EndPointURI.BRANCH)
  public ResponseEntity<Object> updateBranchDetails(@Valid @RequestBody BranchDto branchDto,
      HttpServletRequest request) {

    if (!restaurantService.isRestaurantExist(branchDto.getRestaurantId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_ID_NOT_EXIST,
          validationFailureStatusCodes.getRestaurantIdNotExists()), HttpStatus.BAD_REQUEST);
    }

    if (!branchService.isBranchExist(branchDto.getId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
    }

    Branch branch = new Branch();
    Restaurant restaurant = new Restaurant();
    restaurant.setId(branchDto.getRestaurantId());
    branch.setRestaurant(restaurant);
    BeanUtils.copyProperties(branchDto, branch);
    branchService.updateBranch(branch);
    logger.info("update Branch");
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.UPDATE_BRANCH), HttpStatus.OK);
  }

  @GetMapping(EndPointURI.BRANCH_BY_RESTAURANT_ID)
  public ResponseEntity<Object> getBranchDetails(@PathVariable Long id) {
    if (restaurantService.isRestaurantExist(id)) {
      List<Branch> branchList = branchService.getBranchByRestaurantId(id);
      List<BranchDto> branchDto = new ArrayList<>();
      BeanUtils.copyProperties(branchList, branchDto);
      logger.info("Get Branch Details By Restaurant Id");
      return new ResponseEntity<>(
          new ContentResponse<>(Constants.BRANCH, branchList, RestApiResponseStatus.OK),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_ID_NOT_EXIST,
        validationFailureStatusCodes.getRestaurantIdNotExists()), HttpStatus.BAD_REQUEST);
  }


  @GetMapping(EndPointURI.BRANCH_BY_BRANCH_NAME)
  public ResponseEntity<Object> getBranchIdByBranchName(@PathVariable String branchName) {
    Branch branch = branchRepository.findByBranchName(branchName).orElse(null);
    if (branch != null) {

      return new ResponseEntity<>(branch.getId(), HttpStatus.OK);
    }
    return new ResponseEntity<>(0, HttpStatus.OK);
  }

  @GetMapping(EndPointURI.RESTAURANT_BY_RESTAURANT_NAME)
  public ResponseEntity<Object> getRestaurantIdByRestaurantName(@PathVariable String restaurantName) {
    Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName).orElse(null);

    if (restaurant != null) {
      return new ResponseEntity<>(restaurant.getId(), HttpStatus.OK);
    }
    return new ResponseEntity<>(0, HttpStatus.OK);
  }


  @GetMapping(EndPointURI.BRANCH_NAME)
  public ResponseEntity<Object> getBranchDetailsByBranchName(@PathVariable Long id) {
    Branch branch = branchRepository.findById(id).orElse(null);
    CorporateBranchResponseDto corporateBranchResponseDto = new CorporateBranchResponseDto();
    if (branch != null) {
      BeanUtils.copyProperties(branch, corporateBranchResponseDto);
      return new ResponseEntity<>(corporateBranchResponseDto, HttpStatus.OK);
    }
    return new ResponseEntity<>("not exists", HttpStatus.BAD_REQUEST);
  }

  @GetMapping(EndPointURI.RESTAURANT_NAME)
  public ResponseEntity<Object> getRestaurantDetailsByRestaurantName(@PathVariable Long id) {
    Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
    CorporateRestaurantResponseDto corporateRestaurantResponseDto = new CorporateRestaurantResponseDto();
    if (restaurant != null) {
      BeanUtils.copyProperties(restaurant, corporateRestaurantResponseDto);
      return new ResponseEntity<>(corporateRestaurantResponseDto, HttpStatus.OK);
    }

    return new ResponseEntity<>("restaurant not exists", HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(value = EndPointURI.BRANCH_BY_ID)
  public ResponseEntity<Object> deleteBranch(@PathVariable Long id) {
    if (branchService.isBranchExist(id)) {
      try {
        logger.info("Delete Branch By Id");
        branchService.deleteBranchById(id);
        return new ResponseEntity<>(
            new BasicResponse<>(RestApiResponseStatus.OK, Constants.DELETE_BRANCH), HttpStatus.OK);

      } catch (DataIntegrityViolationException e) {
        logger.info("Delete Branch id {} is Depend", id);
        return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_ID_DEPEND,
            validationFailureStatusCodes.getBranchIdDepend()), HttpStatus.BAD_REQUEST);
      }
    }
    logger.info("Delete Branch Id : id {} Not exists", id);
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.BRANCH_ID_NOT_EXIST),
        HttpStatus.OK);
  }
}
