package it.itwtech.ateauth.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import it.itwtech.ateauth.dto.BranchAdminUserDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.NotificationReceipientsResponseDto;
import it.itwtech.ateauth.dto.OTPdto;
import it.itwtech.ateauth.dto.OtpResendDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.dto.UserResetPasswordDto;
import it.itwtech.ateauth.dto.UserResponseDto;
import it.itwtech.ateauth.dto.changePasswordDto;
import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;
import it.itwtech.ateauth.repositories.DrivingLicenseTypeRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.repositories.VerificationTokenRepository;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.responses.ContentResponse;
import it.itwtech.ateauth.responses.ValidationFailureResponse;
import it.itwtech.ateauth.service.BranchService;
import it.itwtech.ateauth.service.MailNotificationsServices;
import it.itwtech.ateauth.service.RestaurantService;
import it.itwtech.ateauth.service.TextNotificationService;
import it.itwtech.ateauth.service.UserService;
import it.itwtech.ateauth.utils.Constants;
import it.itwtech.ateauth.utils.EndPointURI;
import it.itwtech.ateauth.utils.PropertyFileField;
import it.itwtech.ateauth.utils.Utils;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private MailNotificationsServices mailNotificationsServices;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private PropertyFileField propertyFileValue;
  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private BranchService branchService;
  @Autowired
  private DrivingLicenseTypeRepository drivingLicenseTypeRepository;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  @Autowired
  private TextNotificationService textNotificationService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @GetMapping(value = EndPointURI.USER_BY_ID)
  ResponseEntity<Object> getUserById(@PathVariable Long id) {
    if (userService.isUserExist(id)) {
      Optional<User> userOptional = userService.getUserById(id);
      User user = userOptional.get();
      UserResponseDto userResponseDto = new UserResponseDto();
      BeanUtils.copyProperties(user, userResponseDto);
      return new ResponseEntity<>(
          new ContentResponse<>(Constants.ID, userResponseDto, RestApiResponseStatus.OK),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER_NOT_EXISTS,
        validationFailureStatusCodes.getUserNotExists()), HttpStatus.BAD_REQUEST);
  }

  @PostMapping(value = EndPointURI.FORGOTPASSWORD)
  public ResponseEntity<Object> forgotPassword(@PathVariable String email,
      HttpServletRequest request) {

    if (!userService.isMailExists(email)) {
      return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.MAIL,
          validationFailureStatusCodes.getUserMailNotExist()), HttpStatus.BAD_REQUEST);
    }
    if (userService.findByMail(email).getStatus().equals(false)) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER,
          validationFailureStatusCodes.getUserNotActive()), HttpStatus.BAD_REQUEST);
    }
    userService.forgotPassword(email, request);
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.SENT_SUCCESS), HttpStatus.OK);
  }

  @PutMapping(value = EndPointURI.USER_PASSWORD_RESET)
  ResponseEntity<Object> resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto) {

    if (userService.isTokenExist(userResetPasswordDto.getToken())) {

      VerificationToken verificationToken =
          userService.getUserByToken(userResetPasswordDto.getToken());
      User user = userService.getById(verificationToken.getUser().getId());

      Timestamp localTime = Utils.timeFormat();

      if (verificationToken.getExpiryDate().after(localTime)) {

        user.setPassword(userResetPasswordDto.getPassword());
        userService.resetPassword(user);
        mailNotificationsServices.passwordResetSuccess(user.getEmail());

        return new ResponseEntity<>(
            new BasicResponse<>(RestApiResponseStatus.OK, Constants.PASSWORD_RESET_SUCCESS),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_EXPIRED,
          validationFailureStatusCodes.getUserTokenExpired()), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_INVALID,
        validationFailureStatusCodes.getUserTokenInvalid()), HttpStatus.BAD_REQUEST);
  }

  @PutMapping(value = EndPointURI.CHANGE_PASSWORD)
  public ResponseEntity<Object> changePassword(
      @Valid @RequestBody changePasswordDto changePasswordDto, Principal principal) {
    if (!userService.isMailExists(principal.getName())) {
      logger.info("changePassword: mail not Exit");
      return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.MAIL,
          validationFailureStatusCodes.getEmailNotExit()), HttpStatus.BAD_REQUEST);
    }

    String password = userService.getPassword(principal.getName());

    if (bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), password)) {
      String code = bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword());
      userService.changePassword(code, principal.getName());

      logger.info("changePassword: password changed success");
      return new ResponseEntity<Object>(
          new BasicResponse<>(RestApiResponseStatus.OK, Constants.PASSWORD_CHANGED_SUCCESS),
          HttpStatus.OK);
    }
    logger.info("changePassword: old password not match");
    return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.PASSWORD,
        validationFailureStatusCodes.getGetPasswordNotMatch()), HttpStatus.BAD_REQUEST);
  }

  @PutMapping(value = EndPointURI.OTP_VERIFICATION)
  public ResponseEntity<Object> userVerification(@Valid @RequestBody OTPdto otPdto) {


    if (!userService.isMailExists(otPdto.getEmail())) {
      return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.MAIL,
          validationFailureStatusCodes.getUserMailNotExist()), HttpStatus.BAD_REQUEST);
    }
    User user = userRepository.findByEmail(otPdto.getEmail());

    if (user.getStatus()) {
      return new ResponseEntity<Object>(
          new ValidationFailureResponse(Constants.USER_ALREADY_VERIFIED,
              validationFailureStatusCodes.getAlreadyVerified()),
          HttpStatus.BAD_REQUEST);
    }

    if (userService.isTokenExist(otPdto.getOtp())) {
      VerificationToken verificationToken = userService.getUserByToken(otPdto.getOtp());
      Timestamp localTime = Utils.timeFormat();
      if (verificationToken.getExpiryDate().after(localTime)) {

        String password = userService.setStatusTrue(user.getEmail());
        try {
          textNotificationService.sendTemporaryPassword(user, password);
        } catch (HttpClientErrorException httpClientErrorException) {
          return new ResponseEntity<>(
              new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
                  validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
              HttpStatus.BAD_REQUEST);
        }
        return userService.addCorporateUser(user);
      }

      if (!verificationToken.getToken().equals(otPdto.getOtp())) {
        return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.OTP_NOT_EQUAL,
            validationFailureStatusCodes.getTokenNotEqual()), HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_EXPIRED,
          validationFailureStatusCodes.getUserTokenExpired()), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_INVALID,
        validationFailureStatusCodes.getUserTokenInvalid()), HttpStatus.BAD_REQUEST);
  }

  @PostMapping(value = EndPointURI.USER)
  public ResponseEntity<Object> signUpUser(@Valid @RequestBody UserDto userDto,
      HttpServletRequest request) {

    if (userService.isMailExists(userDto.getEmail())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.EMAIL_ALREADY_EXITS,
          validationFailureStatusCodes.getEmailAlreadyExist()), HttpStatus.BAD_REQUEST);
    }
    if (userService.isMobileNoAlreadyExists(userDto.getMobileNumber())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.MOBILE_NO_ALREADY_EXITS,
          validationFailureStatusCodes.getMobileNoAlreadyExit()), HttpStatus.BAD_REQUEST);
    }

    if (userService.isNicNoAlreadyExists(userDto.getNic())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.NIC_ALREADY_EXITS,
          validationFailureStatusCodes.getNicNoAlreadyExit()), HttpStatus.BAD_REQUEST);
    }
    try {
      String verificationToken = userService.registerUser(userDto, request);
      textNotificationService.sendOTPforStandAlone(userDto, verificationToken);
    } catch (HttpClientErrorException httpClientErrorException) {
      return new ResponseEntity<>(
          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.SEND_VERIFICATION_MAIL_SUCCESS),
        HttpStatus.OK);
  }

  @PutMapping(value = EndPointURI.USER_STATUS)
  public ResponseEntity<Object> changeUserStatus(@PathVariable String userEmail,
      @PathVariable Boolean status) {
    if (!userService.isEmailExists(userEmail)) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER,
          validationFailureStatusCodes.getUserNotExist()), HttpStatus.BAD_REQUEST);
    }
    if (userService.getByUserEmail(userEmail).getStatus().equals(false)) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER,
          validationFailureStatusCodes.getUserAlreadyDeactive()), HttpStatus.BAD_REQUEST);
    }

    userService.userStatus(userEmail);
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.USER_STATUS_CHANGE_SUCCESS),
        HttpStatus.OK);
  }

  @GetMapping(value = EndPointURI.USER_BY_EMAIL)
  public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
    User userobj = userService.getByUserEmail(email);
    if (userobj != null) {
      logger.info("Get user by email");
      return new ResponseEntity<>(userobj, HttpStatus.OK);
    }

    return new ResponseEntity<>("user not exits", HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = EndPointURI.USER_BY_ID_WITHOUT_CONTENTRESPONSE)
  ResponseEntity<Object> getUserByIdWithoutContentResponse(@PathVariable Long id) {
    if (userService.isUserExist(id)) {
      Optional<User> userOptional = userService.getUserById(id);
      User user = userOptional.get();
      UserResponseDto userResponseDto = new UserResponseDto();
      BeanUtils.copyProperties(user, userResponseDto);
      return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
    return new ResponseEntity<>("usernotexist", HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = EndPointURI.CHECK)
  Boolean checkUserExitsByUserId(@PathVariable Long id) {
    if (userService.isUserExist(id)) {

      return true;
    } else {
      return false;
    }
  }

  @PostMapping(value = EndPointURI.BRANCH_ADMIN_REGISTER)
  public ResponseEntity<Object> createBranchAdmin(
      @Valid @RequestBody BranchAdminUserDto branchAdminUserDto, HttpServletRequest request) {
    if (!restaurantService.isRestaurantExist(branchAdminUserDto.getRestaurantId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
    }
    if (!branchService.isBranchExist(branchAdminUserDto.getBranchId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
    }

    if (userService.isMailAndUserTypeExists(branchAdminUserDto.getEmail(),
        branchAdminUserDto.getUserType())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.EMAIL_ALREADY_EXITS,
          validationFailureStatusCodes.getEmailAlreadyExist()), HttpStatus.BAD_REQUEST);
    }

    String verificationToken = userService.createBranchAdmin(branchAdminUserDto, request);
    try {
      textNotificationService.sendOTPforBranchAdminCreation(branchAdminUserDto, verificationToken);
    } catch (HttpClientErrorException httpClientErrorException) {
      return new ResponseEntity<>(
          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.SEND_VERIFICATION_MAIL_SUCCESS),
        HttpStatus.OK);
  }

  @PostMapping(value = EndPointURI.DELIVERYBOY)
  public ResponseEntity<Object> createDeliveryBoy(@Valid @RequestBody DeliveryBoyDto deliveryBoyDto,
      HttpServletRequest request) {

    if (userService.isMailAndUserTypeExists(deliveryBoyDto.getEmail(), deliveryBoyDto.getUserType())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.EMAIL_ALREADY_EXITS,
          validationFailureStatusCodes.getEmailAlreadyExist()), HttpStatus.BAD_REQUEST);
    }
    if (!restaurantService.isRestaurantExist(deliveryBoyDto.getRestaurantId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.RESTAURANT_NOT_EXITS,
          validationFailureStatusCodes.getRestaurantNotExists()), HttpStatus.BAD_REQUEST);
    }
    if (!branchService.isBranchExist(deliveryBoyDto.getBranchId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.BRANCH_NOT_EXITS,
          validationFailureStatusCodes.getBranchNotExists()), HttpStatus.BAD_REQUEST);
    }
    if (!drivingLicenseTypeRepository.existsById(deliveryBoyDto.getDrivingLicenseTypeId())) {
      return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.DRIVINGLICENSE_NOT_EXITS,
          validationFailureStatusCodes.getDrivingLicenseNotExists()), HttpStatus.BAD_REQUEST);
    }

    String verificationToken = userService.createDeliveryBoy(deliveryBoyDto, request);
    try {
      textNotificationService.sendOTPforDeliveryBoyCreation(deliveryBoyDto, verificationToken);
    } catch (HttpClientErrorException httpClientErrorException) {
      return new ResponseEntity<>(
          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.SEND_VERIFICATION_MAIL_SUCCESS),
        HttpStatus.OK);
  }

  @PutMapping(value = EndPointURI.OTP_VERIFICATION_REGISTER)
  public ResponseEntity<Object> userVerificationForRegisteredUser(
      @Valid @RequestBody OTPdto otPdto) {

    User userDetails = userRepository.findByEmail(otPdto.getEmail());

    if (userDetails.getStatus()) {
      return new ResponseEntity<Object>(
          new ValidationFailureResponse(Constants.USER_ALREADY_VERIFIED,
              validationFailureStatusCodes.getAlreadyVerified()),
          HttpStatus.BAD_REQUEST);
    }

    if (userService.isTokenExist(otPdto.getOtp())) {

      VerificationToken verificationToken = userService.getUserByToken(otPdto.getOtp());
      User user = userService.getById(verificationToken.getUser().getId());

      Timestamp localTime = Utils.timeFormat();

      if (!userRepository.existsByEmail(otPdto.getEmail())) {
        return new ResponseEntity<Object>(new ValidationFailureResponse(Constants.MAIL_NOT_EXISTS,
            validationFailureStatusCodes.getEmailNotExit()), HttpStatus.BAD_REQUEST);
      }

      if (verificationToken.getExpiryDate().after(localTime)) {

        userService.setStatusTrue(user.getEmail());
        mailNotificationsServices.sendAccountCreatedEmailForStandAlone(user.getEmail());

        List responce = restTemplate
            .getForObject("http://" + propertyFileValue.getAnytimeeatStandaloneServer() + ":"
                + propertyFileValue.getAnytimeeatStandalonePort()
                + propertyFileValue.getAnytimeeatStandaloneEndPoint()
                + EndPointURI.NOTIFICATION_POINTS, List.class);
        NotificationReceipientsResponseDto notificationReceipientsResponseDto =
            new NotificationReceipientsResponseDto();
        notificationReceipientsResponseDto.setNotificationPoints(responce);
        notificationReceipientsResponseDto.setUserId(user.getId());
        restTemplate.postForObject(
            "http://" + propertyFileValue.getAnytimeeatStandaloneServer() + ":"
                + propertyFileValue.getAnytimeeatStandalonePort()
                + propertyFileValue.getAnytimeeatStandaloneEndPoint()
                + EndPointURI.NOTIFICATION_RECEPIENT,
            notificationReceipientsResponseDto, BasicResponse.class);

        return new ResponseEntity<>(
            new BasicResponse<>(RestApiResponseStatus.OK, Constants.ADD_USER_SUCCESS),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_EXPIRED,
          validationFailureStatusCodes.getUserTokenExpired()), HttpStatus.BAD_REQUEST);

    }
    return new ResponseEntity<>(new ValidationFailureResponse(Constants.TOKEN_INVALID,
        validationFailureStatusCodes.getUserTokenInvalid()), HttpStatus.BAD_REQUEST);

  }

  @PostMapping(value = EndPointURI.CSV_UPLOAD)
  public ResponseEntity<Object> uploadfile(@RequestParam("file") @Valid MultipartFile file) {
    String message = "";
    if (userService.hasCSVFormat(file)) {
      try {
        message = file.getOriginalFilename();
        logger.info("DeliveryBoy CSV/Excel Import Success");
        return userService.csvUpload(file);
      } catch (Exception e) {
        message = file.getOriginalFilename();
        logger.info("File Type not Excel/CSV{} File", e);
        return new ResponseEntity<Object>(new BasicResponse<>(RestApiResponseStatus.ERROR,
            Constants.BULK_DELIVERYBOY_UPLOAD_FAILD + message), HttpStatus.BAD_REQUEST);
      }
    }
    logger.info("DeliveryBoy CSV/Excel Import Failed");
    return new ResponseEntity<Object>(new BasicResponse<>(RestApiResponseStatus.ERROR,
        Constants.BULK_DELIVERYBOY_UPLOAD_ERROR + message), HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = EndPointURI.CSV_DOWNLOAD)
  public ResponseEntity<Resource> downloadDeliveryBoyDetails() {
    String filename = "DeliveryBoy.csv";
    InputStreamResource file = new InputStreamResource(userService.download());
    logger.info(" CSV Dowload Success ");
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/csv")).body(file);
  }

  @PostMapping(value = EndPointURI.RESEND_OTP)
  public ResponseEntity<Object> resendOtp(@Valid @RequestBody OtpResendDto otpResendDto,
      HttpServletRequest request) {
    VerificationToken verificationToken =
        verificationTokenRepository.findByUserId(otpResendDto.getUserId());
    if (verificationToken.getToken() == null) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER_ALREADY_VERIFIED,
          validationFailureStatusCodes.getAlreadyVerified()), HttpStatus.BAD_REQUEST);
    }

    if (!userService.isUserExist(otpResendDto.getUserId())) {
      return new ResponseEntity<>(new ValidationFailureResponse(Constants.USER_NOT_EXISTS,
          validationFailureStatusCodes.getUserNotExists()), HttpStatus.BAD_REQUEST);
    }

    userService.resendOtp(otpResendDto.getUserId());
    try {
      textNotificationService.sendOtpResendTextNotification(verificationToken, otpResendDto);
    } catch (HttpClientErrorException httpClientErrorException) {
      return new ResponseEntity<>(
          new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
              validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.SEND_VERIFICATION_MAIL_SUCCESS),
        HttpStatus.OK);
  }



}
