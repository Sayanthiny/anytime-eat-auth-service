package it.itwtech.ateauth.service;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.dto.RestaurantAdminUserDto;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.model.RestaurantAdmin;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.RestaurantAdminRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.repositories.VerificationTokenRepository;
import it.itwtech.ateauth.utils.PropertyFileField;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

public class RestaurantAdminServiceImpl implements RestaurantAdminService{
	

	  @Autowired
	  private RestaurantAdminRepository restaurantAdminRepository;
	  @Autowired
	  private UserService userService;
	  @Autowired
	  private RestaurantRepository restaurantRepository;
	  @Autowired
	  private MailNotificationsServices mailNotificationsServices;
	  @Autowired
	  private UserRepository userRepository;
	  @Autowired
	  private BranchRepository branchRepository;
	  @Autowired
	  private PropertyFileField propertyFileField;
	  @Autowired
	  private VerificationTokenRepository verificationTokenRepository;
	  @Autowired
	  private ValidationFailureStatusCodes validationFailureStatusCodes;

	  @Transactional
	  public String saveRestaurantAdmin(@Valid RestaurantAdminUserDto userDto, HttpServletRequest request) {

	    Restaurant restaurant = new Restaurant();
	    restaurant.setRestaurantName(userDto.getRestaurantName());
	    restaurant.setAddress(userDto.getAddress());
	    restaurant.setRegistrationNumber(userDto.getRegistrationNumber());
	    restaurant.setRestaurantPhoneNumber(userDto.getRestaurantPhoneNumber());
	    restaurant.setRestaurantEmail(userDto.getRestaurantEmail());
	    restaurant.setLicenceType(userDto.getLicenceType());
	    Restaurant savedRestaurant = restaurantRepository.save(restaurant);

	    User user = new User();
	    user.setEmail(userDto.getEmail());
	    user.setStatus(false);
	    user.setFirstName(userDto.getFirstName());
	    user.setLastName(userDto.getLastName());
	    user.setNic(userDto.getNic());
	    user.setMobileNumber(userDto.getMobileNumber());
	    user.setUserType(userDto.getUserType());
	    VerificationToken verificationToken = new VerificationToken();
	    verificationToken.setUser(user);
	    verificationToken.setToken(userService.generateOTP());
	    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new java.util.Date());
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
	    verificationToken.setExpiryDate(timestamp);
	    Role role = new Role();
	    role.setId(2L);
	    user.setRole(role);
	    User savedUser = userRepository.save(user);
	    verificationTokenRepository.save(verificationToken);
	    mailNotificationsServices.sendVerifyEmailCorporate(user.getEmail(),
	        verificationToken.getToken());

	    RestaurantAdmin restaurantAdmin = new RestaurantAdmin();
	    restaurantAdmin.setUser(savedUser);
	    restaurantAdmin.setRestaurant(savedRestaurant);
	    restaurantAdminRepository.save(restaurantAdmin);

	    Branch branch = new Branch();
	    branch.setBranchName(restaurantAdmin.getRestaurant().getRestaurantName());
	    branch.setEmail(restaurantAdmin.getRestaurant().getRestaurantEmail());
	    branch.setRestaurant(savedRestaurant);
	    branch.setAddress(restaurantAdmin.getRestaurant().getAddress());
	    branch.setPhoneNumber(restaurantAdmin.getRestaurant().getRestaurantPhoneNumber());
	    branchRepository.save(branch);
	    return verificationToken.getToken();

	  }

	  @Override
	  public RestaurantAdmin findRestaurantAdminById(Long id) {
	    return restaurantAdminRepository.findByUserId(id);
	  }

}
