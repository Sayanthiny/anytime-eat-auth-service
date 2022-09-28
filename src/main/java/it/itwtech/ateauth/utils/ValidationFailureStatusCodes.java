package it.itwtech.ateauth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@PropertySource("classpath:ValidationMessages.properties")
public class ValidationFailureStatusCodes {

  // User
  @Value("${validation.user.notExists}")
  private String userNotExists;
  @Value("${validation.user.tokenInvalid}")
  private String userTokenInvalid;
  @Value("${validation.user.tokenExpired}")
  private String userTokenExpired;

  // user
  @Value("${validation.email.notExist}")
  private String EmailNotExit;
  @Value("${validation.email.status.false}")
  private String UserAlreadyDeactive;
  @Value("${validation.password.notMatch}")
  private String getPasswordNotMatch;
  @Value("${validation.mobileNo.alreadyExist}")
  private String MobileNoAlreadyExit;
  @Value("${validation.nic.alreadyExist}")
  private String NicNoAlreadyExit;

  // forget password
  @Value("${validation.email.alreadyExist}")
  private String userMailAlreadyExist;
  @Value("${validation.user.alreadyExist}")
  private String userNotActive;
  @Value("${validation.user.tokenNotEqual}")
  private String tokenNotEqual;

  // Mail Properties
  @Value("${validation.MailProperties.mailPropertiesNotExists}")
  private String mailPropertiesNotExists;
  @Value("${validation.MailProperties.statusTrue}")
  private String statusTrue;
  @Value("${validation.email.alreadyExist}")
  private String emailAlreadyExist;

  // user
  @Value("${validation.user.notExist}")
  private String UserNotExist;
  @Value("${validation.user.notActive}")
  private String UserNotActive;
  @Value("${validation.user.userMailNotExist}")
  private String userMailNotExist;
  @Value("${validation.user.alreadyVerified}")
  private String alreadyVerified;

  // branchAdmin
  @Value("${validation.branchAdmin.depended}")
  private String branchAdminDepended;
  @Value("${validation.branchAdmin.notExists}")
  private String branchAdminNotExists;

  // deliveryBoy
  @Value("${validation.deliveryBoy.notExists}")
  private String deliveryBoyNotExists;

  // restaurant
  @Value("${validation.restaurantName.alreadyExist}")
  private String restaurantNameAlreadyExists;
  @Value("${validation.restaurantEmail.alreadyExist}")
  private String restaurantEmailAlreadyExists;
  @Value("${validation.restaurantId.notExist}")
  private String restaurantIdNotExists;
  @Value("${validation.restaurant.notExist}")
  private String restaurantNotExists;


  // branch
  @Value("${validation.branchId.alreadyExist}")
  private String branchAlreadyExists;
  @Value("${validation.branchId.depend}")
  private String branchIdDepend;
  @Value("${validation.branch.notExist}")
  private String branchNotExists;

  // Role
  @Value("${validation.role.roleNotExists}")
  private String roleNotExists;
  @Value("${validation.rolePermission.NotExists}")
  private String rolePermissionNotExists;

  // module
  @Value("${validation.module.NotExists}")
  private String moduleNotExists;
  @Value("${validation.module.AlreadyExists}")
  private String moduleAlreadyExists;

  // driving licensetype
  @Value("${validation.drivingLicense.NotExists}")
  private String drivingLicenseNotExists;
  
  // text notification
  @Value("${validation.textNotification.balance.notEnough}")
  private String textNotificationBalanceNotEnough;
}
