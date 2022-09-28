package it.itwtech.ateauth.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import it.itwtech.ateauth.dto.BranchAdminUserDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.enums.UserType;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;

public interface UserService {

  public void save(User user);

  public Optional<User> update(Long userId, User user);

  public void delete(Long userId);

  public List<User> getUsers();

  public Optional<User> getUserById(Long id);

  public User getUserByUserName(String userName);

  public void changePassword(String newPassword, String email);

  public boolean isMailExists(String email);

  public String getPassword(String email);

  public boolean isUserExist(Long id);

  public boolean isTokenExist(String token);

  public VerificationToken getUserByToken(String token);

  public void resetPassword(User user);

  public void userStatus(String email);

  public boolean isEmailExists(String userEmail);

  public User getByUserEmail(String email);

  public void emailVerification(String token, String email);

  public String generatePassword();

  public String forgotPassword(String mail, HttpServletRequest request);

  public String generateToken();

  public String generateOTP();

  public User findByMail(String email);

  public User getById(Long id);

  public String getSiteURL(HttpServletRequest request);

  public String registerUser(UserDto userDto, HttpServletRequest request);

  public String registerUserCorporate(User user, HttpServletRequest request);

  public String setStatusTrue(String email);

  public String createBranchAdmin(BranchAdminUserDto branchAdminUserDto, HttpServletRequest request);

  public String createDeliveryBoy(DeliveryBoyDto deliveryBoyDto, HttpServletRequest request);

  public ResponseEntity<Object> csvUpload(MultipartFile file);

  public boolean isDeliveryBoyFirstNameExists(String firstName);

  public boolean isDeliveryBoyLastNameExists(String lastName);

  public boolean isDeliveryBoyNicExists(String nic);

  public boolean isDeliveryBoyDrivingLicenseNoExists(String drivingLicenseNo);

  public boolean isDeliveryBoyDrivingLicenseTypeExists(String drivingLicenseType);

  public boolean isDeliveryBoyPhoneNoExists(String phoneNo);

  public boolean isDeliveryBoyEmailExists(String email);

  public boolean isDeliveryBoyUserTypeExists(String userType);

  public boolean hasCSVFormat(MultipartFile file);

  public ByteArrayInputStream download();

  public void sendBranchAllocationNotificationToDeliveryBoy(User user);

  public boolean isMailAndUserTypeExists(String mail, UserType userType);

  public boolean isMobileNoAlreadyExists(String mobileNumber);

  public boolean isNicNoAlreadyExists(String nic);

  public ResponseEntity<Object> addCorporateUser(User user);
  
  public void resendOtp(Long userId);

}
