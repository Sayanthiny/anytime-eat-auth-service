package it.itwtech.ateauth.utils;

public class Constants {

  private Constants() {

  }

  // common
  public static final String ID = "id";
  public static final String ROLE_CLIENT = "ROLE_CLIENT";
  public static final String TOKEN_NOT_PRESENT = "Token not present in header";

  // user
  public static final String USER_NOT_EXISTS = "User not Exists";
  public static final String TOKEN_INVALID = "Token Invalid";
  public static final String TOKEN_EXPIRED = "Token Expired";
  public static final String PASSWORD_RESET_SUCCESS = "Password Reset Successfully";
  public static final String USER = "User";
  public static final String SENT_SUCCESS = "Your Forgot password Request Sent Success";
  public static final String ADD_USER_SUCCESS = "User Added Successfully";
  public static final String CHANGE_PASSWORD = "changePassword";
  public static final String PASSWORD_CHANGED_SUCCESS = "Password Changed Successfully";
  public static final String PASSWORD = "password";
  public static final String USER_STATUS_CHANGE_SUCCESS = "User Deactivate Sucessfully";
  public static final String SEND_VERIFICATION_MAIL_SUCCESS = "Send Verification Mail Success";
  public static final String MAIL = "mail";
  public static final String MAIL_NOT_EXISTS = "User E-Mail not Exits";
  public static final String EMAIL_ALREADY_EXITS = "Email Already Exists";
  public static final String VERIFICATION_PATH = "/api/v1/email-verification/email/";
  public static final String VERIFICATION_TOKEN = "/token/";
  public static final String OTP_NOT_EQUAL = "OTP not match";
  public static final String USER_ALREADY_VERIFIED = "User already verified";
  public static final String MOBILE_NO_ALREADY_EXITS = "Mobile No Already Exists";
  public static final String NIC_ALREADY_EXITS = "Nic Already Exists";

  // email properties
  public static final String MAIL_PROPERTIES = "mailProperties";
  public static final String ADD_MAIL_PROPERTIES__SUCCESS = "Mail Properties Added Successfully";
  public static final String DELETE_MAIL_PROPERTIES_SUCCESS =
      "Mail Properties Deleted Successfully";
  public static final String UPDATE_MAIL_PROPERTIES__SUCCESS =
      "Mail Properties Updated Successfully";

  // role
  public static final String ROLE_NOT_EXIST = "Role not Exist";

  // Restaurant
  public static final String RESTAURANT_NAME_ALREADY_EXITS = "Restaurant Name Already Exists";
  public static final String RESTAURANT_EMAIL_ALREADY_EXITS = "Restaurant Email Already Exists";
  public static final String RESTAURANT_ID_NOT_EXIST = "Restaurant Id Not Exists";
  public static final String RESTAURANT_NOT_EXITS = "Restaurant Not Exists";

  // branch
  public static final String BRANCH = "Branch";
  public static final String CREATE_BRANCH = "Branch Created Successfully";
  public static final String UPDATE_BRANCH = "Branch Updated Successfully";
  public static final String DELETE_BRANCH = "Branch Deleted Successfully";
  public static final String BRANCH_ID_DEPEND = "Branch Id Depend";
  public static final String BRANCH_ID_NOT_EXIST = "Branch Id Not Exist";
  public static final String BRANCH_ID_ALREADY_EXIST = "Branch Id Already Exist";
  public static final String BRANCH_NOT_EXITS = "Branch Not Exists";

  // csv import , export
  public static final String BULK_DELIVERYBOY_SUCCESS = "DeliveryBoy Upload Sucessfully";
  public static final String BULK_DELIVERYBOY_IMPORT = "DeliveryBoy Bulk Import";
  public static final String BULK_DELIVERYBOY_UPLOAD_FAILD = "Employee Upload Failed";
  public static final String BULK_DELIVERYBOY_UPLOAD_ERROR = "Employee Upload Error";

  // branchadmin
  public static final String BRANCH_ADMIN = "Branch Admin";
  public static final String DELETE_BRANCH_ADMIN_SUCCESS = "Branch Admin Deleted successfully";
  public static final String BRANCH_ADMIN_DEPEND = "Branch Admin Depended";
  public static final String BRANCH_ADMIN_NOT_EXITS = "Branch Admin Not Exists";
  public static final String BRANCH_ADMIN_UPDATE_SUCCESS = "Branch Admin Updated Successfully";
  public static final String BRANCH_ADMIN_ALLOCATE_SUCCESS = "Branch Admin Allocated Successfully ";

  // DeliveryBoy
  public static final String DELIVERYBOY = "deliveryBoy";
  public static final String DELIVERYBOY_NOT_EXITS = "DeliveryBoy not Exist";
  public static final String DELIVERYBOY_DELETE_SUCCESS = "DeliveryBoy Deleted Successfully";
  public static final String DELIVERYBOY_UPDATE_SUCCESS = "DeliveryBoy Updated Successfully";
  public static final String DELIVERYBOY_ALLOCATE_SUCCESS = "DeliveryBoy Allocated Successfully";
  public static final String DRIVINGLICENSE_NOT_EXITS = "Driving license Type Not Exists";

  // module
  public static final String MODULE_NOT_EXITS = "Module not Exist";
  public static final String MODULE_ALREADY_EXIST = "Module Already Exist";
  public static final String ADD_ROLEPERMISSION = "Role permission Added Successfully";
  public static final String UPDATE_ROLEPERMISSION = "Role permission Updated Successfully";
  public static final String ROLE_PERMISSION_NOT_EXIST = "Role Permission Not Exist";
  public static final String ROLE_PERMISSION = "rolePermission";

  // text notification
  public static final String TEXT_NOTIFICATION =
      "Account Balance is not Enough to Send this Message";
}
