package it.itwtech.ateauth.service;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import it.itwtech.ateauth.dto.BranchAdminAllocateDto;
import it.itwtech.ateauth.dto.BranchAdminUserDto;
import it.itwtech.ateauth.dto.DeliveryBoyAllocateDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.OtpResendDto;
import it.itwtech.ateauth.dto.RestaurantAdminUserDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.BranchAdmin;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;
import it.itwtech.ateauth.repositories.BranchAdminRepository;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.utils.PropertyFileField;
@Service
public class TextNotificationServiceImpl implements TextNotificationService {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private PropertyFileField propertyFileValue;
  @Autowired
  private BranchAdminRepository branchAdminRepository;
  @Autowired
  private BranchRepository branchRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;
  @Autowired
  private UserRepository userRepository;


  @Transactional
  public void sendOTPforStandAlone(UserDto userDto, String verificationToken) {
    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + userDto.getMobileNumber()
            + "&message=" + "Use the code:" + verificationToken + " to verify your account",
        String.class);
  }


  @Transactional
  public void sendTemporaryPassword(User user, String password) {
    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + user.getMobileNumber()
            + "&message=" + "Welcome To Fleet Management. This is your User Name :"
            + user.getEmail() + " Password :" + password,
        String.class);

  }


  @Override
  public void sendOTPforCorporate(RestaurantAdminUserDto userDto, String verificationToken) {
    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + userDto.getMobileNumber()
            + "&message=" + "Use the code:" + verificationToken + " to verify your account",
        String.class);
  }


  @Override
  public void sendOTPforBranchAdminCreation(BranchAdminUserDto branchAdminUserDto,
      String verificationToken) {
    restTemplate.postForLocation("https://app.notify.lk/api/v1/send?" + "user_id="
        + propertyFileValue.getNotifyAppUserId() + "&api_key="
        + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
        + propertyFileValue.getNotifyAppSenderId() + "&to=" + branchAdminUserDto.getMobileNumber()
        + "&message=" + "Use the code:" + verificationToken + " to verify your account",
        String.class);

  }


  @Override
  public void sendOTPforDeliveryBoyCreation(DeliveryBoyDto deliveryBoyDto, String verificationToken) {
    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + deliveryBoyDto.getMobileNumber()
            + "&message=" + "Use the code:" + verificationToken + " to verify your account",
        String.class);

  }


  @Override
  public void sendBranchAdminAllocationTextNotification(String oldBranch,
      BranchAdminAllocateDto branchAdminAllocateDto) {
    User branchAdminUser = userRepository.findById(branchAdminAllocateDto.getUserId()).get();
    BranchAdmin branchAdmin =
        branchAdminRepository.findByUserId(branchAdminAllocateDto.getUserId());
    Branch branchDetail = branchRepository.findById(branchAdmin.getBranch().getId()).get();
    Restaurant restaurantDetail = restaurantRepository.findById(branchAdminAllocateDto.getRestaurantId()).get();

    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + branchAdminUser.getMobileNumber()
            + "&message=" + "Hi," + branchAdminUser.getLastName()
            + " You are Allocated as a Branch Admin from " + oldBranch + " to "
            + branchDetail.getBranchName() + " restaurant of " + restaurantDetail.getRestaurantName(),
        String.class);

  }


  @Override
  public void sendDeliveryBoyAllocationTextNotification(@Valid DeliveryBoyAllocateDto driverAllocateDto) {
    User user = userRepository.findById(driverAllocateDto.getUserId()).orElse(null);
    Branch branch = branchRepository.findById(driverAllocateDto.getBranchId()).get();
    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileValue.getNotifyAppUserId()
            + "&api_key=" + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
            + propertyFileValue.getNotifyAppSenderId() + "&to=" + user.getMobileNumber()
            + "&message=" + "You are assigned to " + branch.getBranchName() + " branch",
        String.class);

  }


  @Override
  public void sendOtpResendTextNotification(VerificationToken verificationToken,
      @Valid OtpResendDto otpResendDto) {
    User user = userRepository.getById(otpResendDto.getUserId());
    restTemplate.postForLocation("https://app.notify.lk/api/v1/send?" + "user_id="
        + propertyFileValue.getNotifyAppUserId() + "&api_key="
        + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
        + propertyFileValue.getNotifyAppSenderId() + "&to=" + user.getMobileNumber() + "&message="
        + "Use the code:" + verificationToken.getToken() + " to verify your account", String.class);

  }


  @Override
  public void sendOTPforDeliveryBoyCreationBulkImport(User user, VerificationToken verificationToken) {
    restTemplate.postForLocation("https://app.notify.lk/api/v1/send?" + "user_id="
        + propertyFileValue.getNotifyAppUserId() + "&api_key="
        + propertyFileValue.getNotifyAppApiKey() + "&sender_id="
        + propertyFileValue.getNotifyAppSenderId() + "&to=" + user.getMobileNumber() + "&message="
        + "Use the code:" + verificationToken.getToken() + " to verify your account", String.class);

  }


}
