package it.itwtech.ateauth.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import it.itwtech.ateauth.dto.BranchAdminUserDto;
import it.itwtech.ateauth.dto.DeliveryBoyCsvDownloadDto;
import it.itwtech.ateauth.dto.DeliveryBoyCsvUploadDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.enums.UserType;
import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.model.BranchAdmin;
import it.itwtech.ateauth.model.DeliveryBoy;
import it.itwtech.ateauth.model.Restaurant;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;
import it.itwtech.ateauth.repositories.BranchAdminRepository;
import it.itwtech.ateauth.repositories.BranchRepository;
import it.itwtech.ateauth.repositories.DeliveryBoyRepository;
import it.itwtech.ateauth.repositories.DrivingLicenseTypeRepository;
import it.itwtech.ateauth.repositories.RestaurantRepository;
import it.itwtech.ateauth.repositories.UserRepository;
import it.itwtech.ateauth.repositories.VerificationTokenRepository;
import it.itwtech.ateauth.utils.PropertyFileField;
import it.itwtech.ateauth.utils.ValidationFailureStatusCodes;


@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private MailNotificationsServices mailNotificationsService;
  @Autowired
  private PropertyFileField propertyFileField;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private DeliveryBoyCsvUploadDto deliveryBoyCsvUploadDto;
  @Autowired
  private DrivingLicenseTypeRepository drivingLicenseTypeRepository;
  @Autowired
  private DeliveryBoyCsvDownloadDto deliveryBoyCsvDownloadDto;
  @Autowired
  private BranchAdminRepository branchAdminRepository;
  @Autowired
  private BranchRepository branchRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;
  @Autowired
  private DeliveryBoyRepository deliveryBoyRepository;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private MailNotificationsServices mailNotificationsServices;
  @Autowired
  private PropertyFileField propertyFileValue;
  @Autowired
  private ValidationFailureStatusCodes validationFailureStatusCodes;
  @Autowired
  private TextNotificationService textNotificationService;

  Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  private static String TYPE = "text/csv";



  @Transactional
  public void save(User user) {
    logger.info("saving user");
    userRepository.save(user);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public Optional<User> update(Long userId, User user) {
    return userRepository.findById(userId);
  }

  @Transactional
  public void delete(Long userId) {
    userRepository.deleteById(userId);
  }

  @Transactional
  public List<User> getUsers() {
    List<User> users = userRepository.findAll();
    return users;
  }

  @Transactional
  public Optional<User> getUserById(Long userId) {
    return userRepository.findById(userId);
  }

  @Override
  public User getUserByUserName(String userName) {
    return userRepository.findByEmail(userName);
  }

  @Transactional
  public boolean isUserExist(Long id) {
    return userRepository.existsById(id);
  }

  @Transactional
  public boolean isTokenExist(String token) {
    return verificationTokenRepository.existsByToken(token);
  }

  @Transactional
  public VerificationToken getUserByToken(String token) {
    return verificationTokenRepository.findByToken(token);
  }

  @Transactional
  public void resetPassword(User user) {
    String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encryptedPassword);
    userRepository.save(user);
  }

  @Override
  public boolean isMailExists(String mail) {
    return userRepository.existsByEmail(mail);
  }

  @Transactional
  public void emailVerification(String token, String email) {
    User user = userRepository.findByEmail(email);
    user.setStatus(true);
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setToken(null);
    user.setPassword(generatePassword());
    String password = user.getPassword();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    mailNotificationsService.sendTemporaryUserPasswordEmail(email, user, password);
    userRepository.save(user);
  }

  @Transactional
  public void changePassword(String newPassword, String email) {
    User user = userRepository.findByEmail(email);
    user.setPassword(newPassword);
    userRepository.save(user);
  }

  @Transactional
  public String getPassword(String email) {
    return userRepository.findByEmail(email).getPassword();
  }

  @Transactional
  public String forgotPassword(String mail, HttpServletRequest request) {
    User user = userRepository.findByEmail(mail);
    VerificationToken tokeVerificationToken = new VerificationToken();
    tokeVerificationToken.setUser(user);
    tokeVerificationToken.setToken(generateToken());
    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new java.util.Date());
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
    tokeVerificationToken.setExpiryDate(timestamp);
    verificationTokenRepository.save(tokeVerificationToken);

    String origin = request.getHeader("Origin");
    mailNotificationsService.forgotMail(user.getEmail(), tokeVerificationToken.getToken(), origin);
    return tokeVerificationToken.getToken();
  }

  @Transactional
  public String generateToken() {
    StringBuilder token = new StringBuilder();
    return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString())
        .toString();
  }

  @Transactional
  public String generateOTP() {
    String SALTCHARS = "1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 6) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }

  @Transactional
  public String generatePassword() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuwxyz";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 10) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }

  @Transactional
  public String getSiteURL(HttpServletRequest request) {
    String siteURL = request.getRequestURL().toString();
    return siteURL.replace(request.getServletPath(), "");

  }

  @Transactional
  public String registerUser(UserDto userDto, HttpServletRequest request) {
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setStatus(false);
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setNic(userDto.getNic());
    user.setMobileNumber(userDto.getMobileNumber());
    user.setUserType(userDto.getUserType());
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setToken(generateOTP());
    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new java.util.Date());
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
    verificationToken.setExpiryDate(timestamp);

    if (user.getUserType() == UserType.STANDALONEUSER) {
      Role role = new Role();
      role.setId(5L);
      user.setRole(role);
    }
    if (user.getUserType() == UserType.RESTAURANTDELIVERYBOY) {
      Role role = new Role();
      role.setId(4L);
      user.setRole(role);
    }
    userRepository.save(user);
    verificationTokenRepository.save(verificationToken);
    mailNotificationsService.sendVerifyEmail(user.getEmail(), verificationToken.getToken());
    return verificationToken.getToken();

  }

  @Override
  public User findByMail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public void userStatus(String email) {
    User user = userRepository.findByEmail(email);
    user.setStatus(false);
    mailNotificationsService.sendDeactivateStatusEmail(email, user);
    userRepository.save(user);

  }

  @Transactional
  public User getById(Long id) {
    return userRepository.getById(id);
  }

  @Transactional
  public boolean isEmailExists(String userEmail) {
    return userRepository.existsByEmail(userEmail);
  }

  @Override
  public User getByUserEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public String setStatusTrue(String email) {
    User user = userRepository.findByEmail(email);
    user.setId(user.getId());
    user.setEmail(user.getEmail());
    user.setRole(user.getRole());
    VerificationToken tokeVerificationToken = verificationTokenRepository.findByUserEmail(email);
    tokeVerificationToken.setToken(null);
    user.setStatus(true);
    String password = null;
    if (user.getUserType() == UserType.RESTAURANTADMIN || user.getUserType() == UserType.RESTAURANTDELIVERYBOY
        || user.getUserType() == UserType.RESTAURANTBRANCHADMIN) {
      user.setPassword(generatePassword());
      password = user.getPassword();
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      mailNotificationsServices.sendTemporaryUserPasswordEmail(user.getEmail(), user, password);
    } else {
      user.setPassword(user.getPassword());
    }
    if (user.getUserType() == UserType.RESTAURANTBRANCHADMIN) {
      BranchAdmin branchAdmin = branchAdminRepository.findByUserEmail(user.getEmail());
      mailNotificationsService.sendBranchAdminAllocation(user.getEmail(), branchAdmin);
    }
    if (user.getUserType() == UserType.RESTAURANTDELIVERYBOY) {
      DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUserEmail(user.getEmail());
      mailNotificationsService.sendDeliveryBoyAllocationEmail(user.getEmail(), deliveryBoy);
    }
    userRepository.save(user);
    return password;

  }

  @Transactional
  public String createBranchAdmin(BranchAdminUserDto branchAdminUserDto,
      HttpServletRequest request) {
    User user = new User();
    user.setEmail(branchAdminUserDto.getEmail());
    user.setFirstName(branchAdminUserDto.getFirstName());
    user.setLastName(branchAdminUserDto.getLastName());
    user.setNic(branchAdminUserDto.getNic());
    user.setMobileNumber(branchAdminUserDto.getMobileNumber());
    user.setUserType(branchAdminUserDto.getUserType());
    user.setStatus(false);
    Role role = new Role();
    role.setId(3L);
    user.setRole(role);
    userRepository.save(user);
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setToken(generateOTP());
    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new java.util.Date());
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
    verificationToken.setExpiryDate(timestamp);
    verificationTokenRepository.save(verificationToken);
    mailNotificationsService.sendVerifyEmail(user.getEmail(), verificationToken.getToken());

    Branch branch = new Branch();
    branch.setBranchName(branchAdminUserDto.getBranchName());
    branch.setId(branchAdminUserDto.getBranchId());
    Restaurant restaurant = new Restaurant();
    restaurant.setId(branchAdminUserDto.getRestaurantId());
    BranchAdmin branchAdmin = new BranchAdmin();
    branchAdmin.setUser(user);
    branchAdmin.setBranch(branch);
    branchAdmin.setRestaurant(restaurant);
    branchAdminRepository.save(branchAdmin);
    return verificationToken.getToken();

  }

  @Transactional
  @Async
  public String registerUserCorporate(User user, HttpServletRequest request) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setToken(generateOTP());
    user.setRole(user.getRole());
    if (user.getUserType() == UserType.RESTAURANTDELIVERYBOY) {
      Role role = new Role();
      role.setId(4L);
      user.setRole(role);
    }
    if (user.getUserType() == UserType.RESTAURANTBRANCHADMIN) {
      Role role = new Role();
      role.setId(3L);
      user.setRole(role);
    }
    String pass = generatePassword();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    user.setPassword(bCryptPasswordEncoder.encode(pass));
    userRepository.save(user);

    mailNotificationsService.sendVerifyEmailCorporate(user.getEmail(),
        verificationToken.getToken());
    return verificationToken.getToken();

  }


  public boolean hasCSVFormat(MultipartFile file) {
    if (!TYPE.equals(file.getContentType())) {
      return true;
    }
    return true;
  }

  @Override
  public boolean isDeliveryBoyFirstNameExists(String firstName) {
    return userRepository.existsByFirstName(firstName);
  }

  @Override
  public boolean isDeliveryBoyLastNameExists(String lastName) {
    return userRepository.existsByLastName(lastName);
  }

  @Override
  public boolean isDeliveryBoyNicExists(String nic) {
    return userRepository.existsByNic(nic);
  }

  @Override
  public boolean isDeliveryBoyDrivingLicenseNoExists(String drivingLicenseNo) {
    return userRepository.existsByDrivingLicenseNo(drivingLicenseNo);
  }

  @Override
  public boolean isDeliveryBoyDrivingLicenseTypeExists(String drivingLicenseType) {
    return userRepository.existsByDrivingLicenseType(drivingLicenseType);
  }

  @Override
  public boolean isDeliveryBoyPhoneNoExists(String phoneNo) {
    return userRepository.existsByMobileNumber(phoneNo);
  }

  @Override
  public boolean isDeliveryBoyEmailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public boolean isDeliveryBoyUserTypeExists(String userType) {
    return userRepository.existsByUserType(userType);
  }

  @Transactional
  public ResponseEntity<Object> csvUpload(MultipartFile file) {
    try {
      return converting(importDeliveryBoyDetails(file.getInputStream()), file.getOriginalFilename());
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public Iterable<CSVRecord> importDeliveryBoyDetails(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      return csvParser.getRecords();
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public ResponseEntity<Object> converting(Iterable<CSVRecord> csvRecords, String filename) {
    List<String> deliveryBoyNIC = new ArrayList<String>();
    List<String> deliveryBoyDrivingLicenceNo = new ArrayList<String>();
    List<String> deliveryBoyDrivingLicenceType = new ArrayList<String>();
    List<String> deliveryBoyPhoneNo = new ArrayList<String>();
    List<String> deliveryBoyEmail = new ArrayList<String>();
    Set<String> deliveryBoyNICSet = new HashSet<String>();
    Set<String> deliveryBoyDrivingLicenceNoSet = new HashSet<String>();
    Set<String> deliveryBoyDrivingLicenceTypeSet = new HashSet<String>();
    Set<String> deliveryBoyPhoneNoSet = new HashSet<String>();
    Set<String> deliveryBoyEmailSet = new HashSet<String>();
    Set<String> deliveryBoyBranchNameSet = new HashSet<String>();
    Set<String> deliveryBoyRestaurantNameSet = new HashSet<String>();


    for (CSVRecord csvRecord : csvRecords) {
      User user = new User();
      if (isDeliveryBoyNicExists(csvRecord.get(deliveryBoyCsvUploadDto.getNic()))) {
        deliveryBoyNIC.add(csvRecord.get(deliveryBoyCsvUploadDto.getNic()));
        logger.info("Bulk>> DeliveryBoy NIC Already Exists:{deliveryBoyCsvUploadDto.getNic()}");
      } else {
        user.setNic(csvRecord.get(deliveryBoyCsvUploadDto.getNic()));
      }

      if (isDeliveryBoyDrivingLicenseNoExists(csvRecord.get(deliveryBoyCsvUploadDto.getDrivingLicenseNo()))) {
        deliveryBoyDrivingLicenceNo.add(csvRecord.get(deliveryBoyCsvUploadDto.getDrivingLicenseNo()));
        logger
            .info("Bulk>> DeliveryBoy Driving License No  Already Exists:{deliveryBoyCsvUploadDto.getNic()}");
      } else {
        user.setDrivingLicenseNo(csvRecord.get(deliveryBoyCsvUploadDto.getDrivingLicenseNo()));
      }

      if (isDeliveryBoyPhoneNoExists(csvRecord.get(deliveryBoyCsvUploadDto.getMobileNumber()))) {
        deliveryBoyPhoneNo.add(csvRecord.get(deliveryBoyCsvUploadDto.getMobileNumber()));
        logger.info("Bulk>> DeliveryBoy Phone No Already Exists:{deliveryBoyCsvUploadDto.getMobileNumber()}");
      } else {
        user.setMobileNumber(csvRecord.get(deliveryBoyCsvUploadDto.getMobileNumber()));
      }

      if (isDeliveryBoyEmailExists(csvRecord.get(deliveryBoyCsvUploadDto.getEmail()))) {
        deliveryBoyEmail.add(csvRecord.get(deliveryBoyCsvUploadDto.getEmail()));
        logger.info("Bulk>> DeliveryBoy Email Already Exists:{deliveryBoyCsvUploadDto.getEmail()}");
      } else {
        user.setEmail(csvRecord.get(deliveryBoyCsvUploadDto.getEmail()));
      }

      user.setFirstName(csvRecord.get(deliveryBoyCsvUploadDto.getFirstName()));
      user.setLastName(csvRecord.get(deliveryBoyCsvUploadDto.getLastName()));
      user.setUserType(UserType.COMPANYDRIVER);

      Optional<DrivingLicenseType> drivingLicenseType = drivingLicenseTypeRepository
          .findByType(csvRecord.get(deliveryBoyCsvUploadDto.getDrivingLicenseType()));
      if (!drivingLicenseType.isPresent()) {
        deliveryBoyDrivingLicenceType.add((csvRecord.get(deliveryBoyCsvUploadDto.getDrivingLicenseType())));
        continue;
      } else {
        user.setDrivingLicenseType(drivingLicenseType.get());
      }

      if (user.getNic() != null && user.getEmail() != null && user.getDrivingLicenseNo() != null) {
        user.setStatus(false);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(generateOTP());
        Role role = new Role();
        role.setId(4L);
        user.setRole(role);
        userRepository.save(user);

        Optional<Branch> deliveryBoyBranch = branchRepository
            .findByBranchName(csvRecord.get(deliveryBoyCsvUploadDto.getDeliveryBoyBranchName()));

        Optional<Restaurant> deliveryBoyRestaurant = restaurantRepository
            .findByRestaurantName(csvRecord.get(deliveryBoyCsvUploadDto.getDeliveryBoyRestaurantName()));

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        Branch branch = new Branch();
        Restaurant restaurant = new Restaurant();
        branch.setId(deliveryBoyBranch.get().getId());
        deliveryBoy.setBranch(branch);
        restaurant.setId(deliveryBoyRestaurant.get().getId());
        deliveryBoy.setRestaurant(restaurant);
        deliveryBoy.setUser(user);
        deliveryBoyRepository.save(deliveryBoy);

        verificationTokenRepository.save(verificationToken);
        mailNotificationsService.sendVerifyEmail(user.getEmail(), verificationToken.getToken());

        try {
          textNotificationService.sendOTPforDeliveryBoyCreationBulkImport(user, verificationToken);
        } catch (HttpClientErrorException httpClientErrorException) {
          return new ResponseEntity<>(
              new ValidationFailureResponse(Constants.TEXT_NOTIFICATION,
                  validationFailureStatusCodes.getTextNotificationBalanceNotEnough()),
              HttpStatus.BAD_REQUEST);
        }
      }
    }
    deliveryBoyNICSet.addAll(deliveryBoyEmail);
    deliveryBoyDrivingLicenceNoSet.addAll(deliveryBoyDrivingLicenceNo);
    deliveryBoyPhoneNoSet.addAll(deliveryBoyPhoneNo);
    deliveryBoyEmailSet.addAll(deliveryBoyEmail);
    deliveryBoyDrivingLicenceTypeSet.addAll(deliveryBoyDrivingLicenceType);
    deliveryBoyBranchNameSet.addAll(deliveryBoyBranchNameSet);
    deliveryBoyRestaurantNameSet.addAll(deliveryBoyRestaurantNameSet);


    ImportErrorResponseDto importErrorResponseDto = new ImportErrorResponseDto();
    importErrorResponseDto.setDeliveryBoyNIC(deliveryBoyNICSet);
    importErrorResponseDto.setDeliveryBoyDrivingLicenceNo(deliveryBoyDrivingLicenceNoSet);
    importErrorResponseDto.setDeliveryBoyPhoneNo(deliveryBoyPhoneNoSet);
    importErrorResponseDto.setDeliveryBoyEmail(deliveryBoyEmailSet);
    importErrorResponseDto.setDeliveryBoyBranchNameSet(deliveryBoyBranchNameSet);
    importErrorResponseDto.setDeliveryBoyRestaurantNameSet(deliveryBoyRestaurantNameSet);


    if (deliveryBoyNICSet.size() == 0 && deliveryBoyDrivingLicenceNoSet.size() == 0
        && deliveryBoyPhoneNoSet.size() == 0 && deliveryBoyEmailSet.size() == 0
        && deliveryBoyBranchNameSet.size() == 0 && deliveryBoyRestaurantNameSet.size() == 0) {
      return new ResponseEntity<>(
          new BasicResponse<>(RestApiResponseStatus.OK, Constants.BULK_DRIVER_SUCCESS + filename),
          HttpStatus.OK);


    }
    return new ResponseEntity<>(new ContentResponse<>(Constants.BULK_DRIVER_IMPORT,
        importErrorResponseDto, RestApiResponseStatus.OK), null, HttpStatus.OK);
  }


  public ByteArrayInputStream exportDeliveryBoyDetails(List<User> users) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      csvPrinter.printRecord(deliveryBoyCsvDownloadDto.getHeaders());
      for (User user : users) {
        if (user.getNic() != null && user.getEmail() != null && user.getDrivingLicenseNo() != null
            && user.getDrivingLicenseType().getType() != null) {
          List<Object> data = Arrays.asList(String.valueOf(user.getId()), user.getFirstName(),
              user.getLastName(), user.getEmail(), user.getMobileNumber(),
              user.getDrivingLicenseNo(), user.getNic(), user.getDrivingLicenseType().getType());
          csvPrinter.printRecord(data);
        }
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

  @Override
  public ByteArrayInputStream download() {
    List<User> user = userRepository.findAll();
    ByteArrayInputStream download = exportDeliveryBoyDetails(user);
    return download;
  }

  @Override
  public String createDeliveryBoy(DeliveryBoyDto deliveryBoyDto, HttpServletRequest request) {
    User user = new User();
    BeanUtils.copyProperties(deliveryBoyDto, user);
    DrivingLicenseType drivingLicenseType =
        drivingLicenseTypeRepository.findById(deliveryBoyDto.getDrivingLicenseTypeId()).get();
    user.setDrivingLicenseType(drivingLicenseType);
    user.setStatus(false);
    if (user.getUserType() == UserType.COMPANYDRIVER) {
      Role role = new Role();
      role.setId(4L);
      user.setRole(role);
    }

    User savedUser = userRepository.save(user);
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setToken(generateOTP());
    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new java.util.Date());
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
    verificationToken.setExpiryDate(timestamp);
    verificationTokenRepository.save(verificationToken);
    mailNotificationsService.sendVerifyEmail(user.getEmail(), verificationToken.getToken());

    DeliveryBoy deliveryBoy = new DeliveryBoy();
    Branch branch = branchRepository.findById(deliveryBoyDto.getBranchId()).orElse(null);
    deliveryBoy.setBranch(branch);
    Restaurant restaurant = restaurantRepository.findById(deliveryBoyDto.getRestaurantId()).orElse(null);
    deliveryBoy.setRestaurant(restaurant);
    deliveryBoy.setUser(savedUser);
    deliveryBoyRepository.save(deliveryBoy);
    return verificationToken.getToken();

  }

  @Override
  public void sendBranchAllocationNotificationToDeliveryBoy(User user) {
    DeliveryBoy DeliveryBoyUserDetails = deliveryBoyRepository.findByUserId(user.getId());

    restTemplate.postForLocation(
        "https://app.notify.lk/api/v1/send?" + "user_id=" + propertyFileField.getNotifyAppUserId()
            + "&api_key=" + propertyFileField.getNotifyAppApiKey() + "&sender_id="
            + propertyFileField.getNotifyAppSenderId() + "&to=" + user.getMobileNumber()
            + "&message=" + "Hi," + user.getLastName() + " You are Allocated as a deliveryBoy to "
            + DeliveryBoyUserDetails.getBranch().getBranchName() + " restaurant of "
            + DeliveryBoyUserDetails.getRestaurant().getRestaurantName(),
        String.class);

    NotificationDto notificationDto = new NotificationDto();
    notificationDto.setRead(false);
    notificationDto.setMessage("You are allocated as a DeliveryBoy");
    notificationDto.setDescription("Hi," + user.getLastName() + " You are Allocated as a DeliveryBoy to "
        + DeliveryBoyUserDetails.getBranch().getBranchName() + " restaurant of "
        + DeliveryBoyUserDetails.getRestaurant().getRestaurantName());
    notificationDto.setUserId(user.getId());
    restTemplate.postForEntity(
        "http://" + propertyFileField.getFleetmanagementCorporateServer() + ":"
            + propertyFileField.getFleetmanagementCorporatePort()
            + propertyFileField.getFleetmanagementCorporateEndPoint() + EndPointURI.NOTIFICATION,
        notificationDto, String.class);

    DeliveryBoyAllocationWebsocketDto deliveryBoyAllocationWebsocketDto = new DeliveryBoyAllocationWebsocketDto();

    deliveryBoyAllocationWebsocketDto.setShortmsg(notificationDto.getMessage());
    deliveryBoyAllocationWebsocketDto.setDetailsmsg(notificationDto.getDescription());
    deliveryBoyAllocationWebsocketDto.setUserId(notificationDto.getUserId());


    restTemplate.postForEntity(
        "http://" + propertyFileField.getWebSocketServer() + ":"
            + propertyFileField.getWebSocketPort() + EndPointURI.DRIVER_ALLOCATION_NOTIFICATION,
        deliveryBoyAllocationWebsocketDto, String.class);

    mailNotificationsService.sendDeliveryBoyAllocationEmail(user.getEmail(), DeliveryBoyUserDetails);

  }


  @Override
  public boolean isMailAndUserTypeExists(String mail, UserType userType) {
    return userRepository.existsByEmailAndUserType(mail, userType);
  }

  @Override
  public boolean isMobileNoAlreadyExists(String mobileNumber) {
    return userRepository.existsByMobileNumber(mobileNumber);
  }

  @Override
  public boolean isNicNoAlreadyExists(String nic) {
    return userRepository.existsByNic(nic);
  }

  @Transactional
  public ResponseEntity<Object> addCorporateUser(User user) {
    List responce =
        restTemplate.getForObject("http://" + propertyFileValue.getFleetmanagementCorporateServer()
            + ":" + propertyFileValue.getFleetmanagementCorporatePort()
            + propertyFileValue.getFleetmanagementCorporateEndPoint()
            + EndPointURI.NOTIFICATION_POINTS, List.class);
    NotificationReceipientsResponseDto notificationReceipientsResponseDto =
        new NotificationReceipientsResponseDto();
    notificationReceipientsResponseDto.setNotificationPoints(responce);
    notificationReceipientsResponseDto.setUserId(user.getId());
    restTemplate.postForObject(
        "http://" + propertyFileValue.getFleetmanagementCorporateServer() + ":"
            + propertyFileValue.getFleetmanagementCorporatePort()
            + propertyFileValue.getFleetmanagementCorporateEndPoint()
            + EndPointURI.NOTIFICATION_RECEPIENT,
        notificationReceipientsResponseDto, BasicResponse.class);
    return new ResponseEntity<>(
        new BasicResponse<>(RestApiResponseStatus.OK, Constants.ADD_USER_SUCCESS), HttpStatus.OK);
  }

  @Transactional
  @Async
  public void resendOtp(Long userId) {

    VerificationToken verificationToken = verificationTokenRepository.findByUserId(userId);
    verificationToken.setToken(generateOTP());
    verificationToken.setUser(verificationToken.getUser());
    int expiryTimeInMinutes = Integer.valueOf(propertyFileField.getVerifyTokenExpire());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new java.util.Date());
    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + expiryTimeInMinutes);
    verificationToken.setExpiryDate(timestamp);
    verificationTokenRepository.save(verificationToken);

    User user = userRepository.getById(userId);
    mailNotificationsService.sendVerifyEmail(user.getEmail(), verificationToken.getToken());
  }


}
