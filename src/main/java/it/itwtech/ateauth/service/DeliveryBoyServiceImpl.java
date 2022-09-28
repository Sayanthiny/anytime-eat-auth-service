package it.itwtech.ateauth.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import it.itwtech.ateauth.dto.BranchResponseDto;
import it.itwtech.ateauth.dto.DeliveryBoyAllocateDto;
import it.itwtech.ateauth.dto.DeliveryBoyAllocationWebsocketDto;
import it.itwtech.ateauth.dto.DeliveryBoyBranchUpdateDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.DeliveryBoyResponseDto;
import it.itwtech.ateauth.dto.NotificationDto;
import it.itwtech.ateauth.dto.RestaurantResponseDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.dto.UserResponseDto;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.DeliveryBoy;
import it.itwtech.ateauth.model.DrivingLicenseType;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.DeliveryBoyRepository;
import it.itwtech.ateauth.repositories.DrivingLicenseTypeRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.PropertyFileField;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

@Service
public class DeliveryBoyServiceImpl implements DeliveryBoyService{

	@Autowired
	  private DeliveryBoyRepository deliveryBoyRepository;
	  @Autowired
	  private UserRepository userRepository;
	  @Autowired
	  private BranchRepository branchRepository;
	  @Autowired
	  private RestaurantRepository restaurantRepository;
	  @Autowired
	  private DrivingLicenseTypeRepository drivingLicenseTypeRepository;
	  @Autowired
	  private UserService userService;
	  @Autowired
	  private ValidationFailureStatusCodes validationFailureStatusCodes;

	  @Autowired
	  private RestTemplate restTemplate;

	  @Autowired
	  private PropertyFileField propertyFileField;


	  @Autowired
	  private MailNotificationsServices mailNotificationsService;

	  @Transactional
	  public boolean isRestaurantIdExists(Long restaurantId) {
	    return deliveryBoyRepository.existsByRestaurantId(restaurantId);
	  }

	  @Transactional
	  public List<DeliveryBoy> getAllDeliveryBoysByRestaurantId(Long restaurantId) {
	    return deliveryBoyRepository.findAllByRestaurantId(restaurantId);
	  }

	  @Transactional
	  public List<DeliveryBoyResponseDto> setDeliveryBoyResponseDto(List<DeliveryBoy> deliveryBoyList) {
	    List<DeliveryBoyResponseDto> deliveryBoyResponseDtolist = new ArrayList<>();
	    for (DeliveryBoy deliveryBoy : deliveryBoyList) {
	      DeliveryBoyResponseDto deliveryBoyResponseDto = new DeliveryBoyResponseDto();

	      UserResponseDto userResponseDto = new UserResponseDto();
	      BeanUtils.copyProperties(deliveryBoy.getUser(), userResponseDto);
	      deliveryBoyResponseDto.setUserResponseDto(userResponseDto);

	      BranchResponseDto branchResponseDto = new BranchResponseDto();
	      BeanUtils.copyProperties(deliveryBoy.getBranch(), branchResponseDto);
	      deliveryBoyResponseDto.setBranchResponseDto(branchResponseDto);

	      RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
	      BeanUtils.copyProperties(deliveryBoy.getRestaurant(), restaurantResponseDto);
	      deliveryBoyResponseDto.setRestaurantResponseDto(restaurantResponseDto);
	      deliveryBoyResponseDtolist.add(deliveryBoyResponseDto);
	    }
	    return deliveryBoyResponseDtolist;
	  }

	  @Transactional
	  public boolean isDeliveryBoyIdExists(Long id) {
	    return deliveryBoyRepository.existsById(id);
	  }

	  @Transactional
	  public void deleteDeliveryBoyById(Long id) {
	    UserDto userDto = new UserDto();
	    User user =
	        userRepository.findById(deliveryBoyRepository.findById(id).get().getUser().getId()).get();
	    BeanUtils.copyProperties(user, userDto);
	    userDto.setStatus(false);

	    User userTwo = new User();
	    BeanUtils.copyProperties(userDto, userTwo);
	    userRepository.save(userTwo);
	    deliveryBoyRepository.deleteById(id);
	  }

	  @Override
	  public void updateDeliveryBoy(@Valid DeliveryBoyDto deliveryBoyDto, HttpServletRequest request) {
	    User user = new User();
	    User userTwo = userService.getByUserEmail(deliveryBoyDto.getEmail());
	    BeanUtils.copyProperties(deliveryBoyDto, user);
	    user.setId(userTwo.getId());
	    user.setUserStatus(userTwo.getUserStatus());
	    user.setStatus(userTwo.getStatus());
	    user.setPassword(userTwo.getPassword());
	    DrivingLicenseType drivingLicenseType =
	        drivingLicenseTypeRepository.findById(deliveryBoyDto.getDrivingLicenseTypeId()).get();
	    user.setDrivingLicenseType(drivingLicenseType);
	    Role role = new Role();
	    role.setId(userTwo.getRole().getId());
	    user.setRole(role);

	    userRepository.save(user);

	    DeliveryBoy deliveryBoy = new DeliveryBoy();
	    Branch branch = branchRepository.findById(deliveryBoyDto.getBranchId()).orElse(null);
	    deliveryBoy.setBranch(branch);
	    Restaurant restaurant = restaurantRepository.findById(deliveryBoyDto.getRestaurantId()).orElse(null);
	    deliveryBoy.setRestaurant(restaurant);
	    deliveryBoy.setUser(user);
	    deliveryBoy.setId(deliveryBoyDto.getDeliveryBoyId());
	    deliveryBoyRepository.save(deliveryBoy);

	  }

	  @Transactional
	  @Async
	  public void allocateDeliveryBoyToBranch(@Valid DeliveryBoyAllocateDto deliveryBoyAllocateDto) {
	    DeliveryBoy deliveryBoyDb = deliveryBoyRepository.findByUserId(deliveryBoyAllocateDto.getUserId());
	    String oldBranch = deliveryBoyDb.getBranch().getBranchName();
	    Branch branch = branchRepository.findById(deliveryBoyAllocateDto.getBranchId()).get();
	    deliveryBoyDb.setBranch(branch);

	    Restaurant restaurant = restaurantRepository.findById(deliveryBoyAllocateDto.getRestaurantId()).get();
	    deliveryBoyDb.setRestaurant(restaurant);

	    User user = userRepository.findById(deliveryBoyAllocateDto.getUserId()).orElse(null);
	    deliveryBoyDb.setUser(user);
	    deliveryBoyRepository.save(deliveryBoyDb);

	    DeliveryBoyBranchUpdateDto deliveryBoyBranchUpdateDto = new DeliveryBoyBranchUpdateDto();
	    deliveryBoyBranchUpdateDto.setBranchName(branch.getBranchName());
	    deliveryBoyBranchUpdateDto.setEmail(user.getEmail());
	    DeliveryBoyAllocationWebsocketDto deliveryBoyAllocationWebsocketDto = new DeliveryBoyAllocationWebsocketDto();

	    NotificationDto notificationDto = new NotificationDto();
	    notificationDto.setRead(false);
	    notificationDto.setMessage("You are allocated as a DeliveryBoy");

	    notificationDto.setDescription(
	        "Hi," + user.getLastName() + "  You are Allocated as a DeliveryBoy from " + oldBranch + " to "
	            + branch.getBranchName() + " restaurant of " + restaurant.getRestaurantName());
	    notificationDto.setUserId(user.getId());
	    restTemplate.postForEntity(
	        "http://" + propertyFileField.getAnytimeeatCorporateServer() + ":"
	            + propertyFileField.getAnytimeeatCorporatePort()
	            + propertyFileField.getAnytimeeatCorporateEndPoint() + EndPointURI.NOTIFICATION,
	        notificationDto, String.class);

	    deliveryBoyAllocationWebsocketDto.setShortmsg(notificationDto.getMessage());
	    deliveryBoyAllocationWebsocketDto.setDetailsmsg(notificationDto.getDescription());
	    deliveryBoyAllocationWebsocketDto.setUserId(notificationDto.getUserId());
	    restTemplate.postForEntity(
	        "http://" + propertyFileField.getWebSocketServer() + ":"
	            + propertyFileField.getWebSocketPort() + EndPointURI.DELIVERYBOY_ALLOCATION_NOTIFICATION,
	        deliveryBoyAllocationWebsocketDto, String.class);
	    mailNotificationsService.sendDeliveryBoyBranchUpdateEmail(deliveryBoyBranchUpdateDto);
	  }

	  @Override
	  public boolean isUserIdExists(Long id) {
	    return deliveryBoyRepository.existsByUserId(id);
	  }

	  @Override
	  public boolean isBranchIdExists(Long branchId) {
	    return deliveryBoyRepository.existsByBranchId(branchId);
	  }

//	  @Override
//	  public List<DeliveryBoy> getAllDeliveryBoysByRestaurantIdBranchId(Long restaurantId, Long branchId) {
//	    return deliveryBoyRepository.findAllByRestaurantIdAndBranchId(restaurantId, branchId);
//	  }

	  @Override
	  public DeliveryBoy findByUserId(Long id) {
	    return deliveryBoyRepository.findByUserId(id);
	  }

	@Override
	public List<DeliveryBoy> getAllDeliveryBoyByRestaurantIdBranchId(Long restaurantId, Long branchId) {
		// TODO Auto-generated method stub
		return deliveryBoyRepository.findAllByRestaurantIdAndBranchId(restaurantId, branchId);
	}


}
