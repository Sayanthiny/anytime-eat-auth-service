package it.itwtech.ateauth.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.itwtech.ateauth.dto.BranchAdminAllocateDto;
import it.itwtech.ateauth.dto.BranchAdminAllocationWebsocketDto;
import it.itwtech.ateauth.dto.BranchAdminResponseDto;
import it.itwtech.ateauth.dto.BranchResponseDto;
import it.itwtech.ateauth.dto.BranchUserDto;
import it.itwtech.ateauth.dto.NotificationDto;
import it.itwtech.ateauth.dto.RestaurantResponseDto;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.BranchAdmin;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.repositories.BranchAdminRepository;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.PropertyFileField;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;



@Service
public class BranchAdminServiceImpl implements BranchAdminService {

  @Autowired
  private BranchAdminRepository branchAdminRepository;
  @Autowired
  private BranchRepository branchRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private PropertyFileField propertyFileField;
  @Autowired
  private EmailClintService emailClintService;
  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;

  @Override
  public List<BranchAdminResponseDto> getAllBranchAdminByRestaurant(Long id) {
    List<BranchAdmin> branchAdminList = branchAdminRepository.findByRestaurantId(id);
    List<BranchAdminResponseDto> branchAdminResponseDtoList = new ArrayList<>();
    for (BranchAdmin branchAdmin : branchAdminList) {
      BranchAdminResponseDto branchAdminResponseDto = new BranchAdminResponseDto();
      BeanUtils.copyProperties(branchAdminResponseDto, branchAdmin);

      RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
      BeanUtils.copyProperties(branchAdmin.getRestaurant(), restaurantResponseDto);
      branchAdminResponseDto.setRestaurantResponseDto(restaurantResponseDto);

      BranchResponseDto branchResponseDto = new BranchResponseDto();
      BeanUtils.copyProperties(branchAdmin.getBranch(), branchResponseDto);
      branchAdminResponseDto.setBranchResponseDto(branchResponseDto);
      branchAdminResponseDtoList.add(branchAdminResponseDto);
    }
    return branchAdminResponseDtoList;
  }

  @Override
  public boolean isBranchAdminExists(Long id) {
    return branchAdminRepository.existsById(id);
  }

  @Override
  public void deleteBranchAdmin(Long id) {
    branchAdminRepository.deleteById(id);
  }

  @Override
  public void updateBranchAdmin(@Valid BranchUserDto branchUserDto, HttpServletRequest request) {
    User user = new User();
    User userTwo = userService.getByUserEmail(branchUserDto.getEmail());
    BeanUtils.copyProperties(branchUserDto, user);
    user.setId(userTwo.getId());
    user.setUserStatus(userTwo.getUserStatus());
    user.setStatus(userTwo.getStatus());
    user.setPassword(userTwo.getPassword());

    Role role = new Role();
    role.setId(userTwo.getRole().getId());
    user.setRole(role);

    userRepository.save(user);

    BranchAdmin branchAdmin = new BranchAdmin();
    Branch branch = branchRepository.findById(branchUserDto.getBranchId()).orElse(null);
    branchAdmin.setBranch(branch);
    Restaurant restaurant = restaurantRepository.findById(branchUserDto.getRestaurantId()).orElse(null);
    branchAdmin.setRestaurant(restaurant);
    branchAdmin.setUser(user);
    branchAdmin.setId(branchUserDto.getBranchAdminId());
    branchAdminRepository.save(branchAdmin);


  }

  @Override
  public Long getBranchAdminByBranchId(Long branchId) {
    if( branchAdminRepository.findByBranchId(branchId)!=null) {
      return branchAdminRepository.findByBranchId(branchId).getUser().getId();
    }
    return 0L;

  }

  @Transactional
  public String  allocateBranchAdmin(@Valid BranchAdminAllocateDto branchAdminAllocateDto) {
    final BranchAdmin branchAdminDb =
        branchAdminRepository.findByUserId(branchAdminAllocateDto.getUserId());
    String oldBranch = branchAdminDb.getBranch().getBranchName();
    Branch branch = new Branch();
    branch.setId(branchAdminAllocateDto.getBranchId());
    branchAdminDb.setBranch(branch);
    Restaurant restaurant = new Restaurant();
    restaurant.setId(branchAdminDb.getRestaurant().getId());
    branchAdminDb.setRestaurant(restaurant);
    User user = new User();
    user.setId(branchAdminDb.getUser().getId());
    branchAdminDb.setUser(user);
    branchAdminRepository.save(branchAdminDb);

    User branchAdminUser = userRepository.findById(user.getId()).get();
    BranchAdmin branchAdmin = branchAdminRepository.findByUserId(branchAdminDb.getUser().getId());
    Branch branchDetail = branchRepository.findById(branchAdmin.getBranch().getId()).get();
    Restaurant restaurantDetail = restaurantRepository.findById(restaurant.getId()).get();
    NotificationDto notificationDto = new NotificationDto();
    notificationDto.setRead(false);
    notificationDto.setMessage("You are allocated as a Branch Admin");
    notificationDto.setDescription("Hi," + branchAdminUser.getLastName()
        + " You are Allocated as a Branch Admin from " + oldBranch + " to "
        + branchDetail.getBranchName() + " restaurant of " + restaurantDetail.getRestaurantName());
    notificationDto.setUserId(branchAdminUser.getId());
    restTemplate.postForEntity(
        "http://" + propertyFileField.getAnytimeeatCorporateServer() + ":"
            + propertyFileField.getAnytimeeatCorporatePort()
            + propertyFileField.getAnytimeeatStandaloneEndPoint() + EndPointURI.NOTIFICATION,
        notificationDto, String.class);

    BranchAdminAllocationWebsocketDto branchAdminAllocationWebsocketDto =
        new BranchAdminAllocationWebsocketDto();
    branchAdminAllocationWebsocketDto.setDetailsmsg(notificationDto.getDescription());
    branchAdminAllocationWebsocketDto.setUserId(notificationDto.getUserId());

   restTemplate.postForEntity("http://" + propertyFileField.getWebSocketServer() + ":"
       + propertyFileField.getWebSocketPort() + EndPointURI.BRANCH_ADMIN_ALLOCATION_NOTIFICATION,
       branchAdminAllocationWebsocketDto, String.class);

    emailClintService.sendHtmlMail(branchAdminUser.getEmail(), "Branch Admin Allocation",
        "Hi," + branchAdminUser.getLastName() + " You are Allocated as a Branch Admin from "
            + oldBranch + " to " + branchDetail.getBranchName() + " restaurant of "
            + restaurantDetail.getRestaurantName());
    return oldBranch;

  }

  @Override
  public boolean isBranchAdminUserExists(Long id) {
    return branchAdminRepository.existsByUserId(id);
  }

  @Override
  public BranchAdmin findRestaurantBranchAdminById(Long id) {
    return branchAdminRepository.findByUserId(id);
  }
}
