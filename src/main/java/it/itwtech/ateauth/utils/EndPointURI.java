package it.itwtech.ateauth.utils;

public class EndPointURI {
  private static final String RESOURCE_BASI_API = "/anytime-ate-resource/api/v1/";
  private static final String BASE_API_PATH = "/api/v1/";
  private static final String CORPORATE_BASE_API = "/anytime-ate-corporate/api/v1/";
  private static final String STANDALONE_BASE_API = "/anytime-ate-standalone/api/v1/";
  private static final String SLASH = "/";
  private static final String ID = "{id}";
  private static final String EMAIL = "{email}";
  private static final String USER_ID = "{id}";
  private static final String BRANCHID = "{branchid}";
  private static final String RESTAURANTID = "{restaurantid}";
//  private static final String VEHICLENUMBER = "{vehicleNumber}";
  private static final String BRANCHNAME = "{branchName}";
  private static final String RESTAURANTNAME = "{restaurantName}";

  // User
  public static final String USER = BASE_API_PATH + "anytime-ate/oauth2/user";
  public static final String USER_BY_ID = USER + SLASH + ID;
  public static final String USER_PASSWORD_RESET = BASE_API_PATH + "userpasswordReset";
  public static final String CHANGE_PASSWORD = BASE_API_PATH + "changePassword";
  public static final String FORGOTPASSWORD = BASE_API_PATH + "email" + SLASH + "{email}";
  public static final String RESET_PASSWORD = BASE_API_PATH + "forgot";
  public static final String EMAIL_VERIFICATION = BASE_API_PATH + "email-verification" + SLASH
      + "email" + SLASH + "{email}" + SLASH + "token" + SLASH + "{token}";
  public static final String USER_BY_ID_WITHOUT_CONTENTRESPONSE =
      BASE_API_PATH + "user" + SLASH + ID;
  public static final String CHECK = BASE_API_PATH + "checkuser" + SLASH + ID;
  public static final String USER_REGISTER = BASE_API_PATH + "register";

  // mail Properties
  public static final String MAIL = BASE_API_PATH + "mail";
  public static final String MAIL_BY_ID = MAIL + SLASH + ID;
  public static final String SEARCH = "search";

  // user
  public static final String USER_STATUS = BASE_API_PATH + "userStatus" + SLASH + "userEmail"
      + SLASH + "{userEmail}" + SLASH + "status" + SLASH + "{status}";
  public static final String EMPLOYEE_BY_USER = BASE_API_PATH + "userStatus" + SLASH + "test";
  public static final String USER_BY_EMAIL = BASE_API_PATH + "userbyemail" + SLASH + EMAIL;
  public static final String NOTIFICATION_RECEPIENT = "notificationReceipient";
  public static final String NOTIFICATION_POINTS = "notificationPoints";

  // OTP verification
  public static final String OTP_VERIFICATION = BASE_API_PATH + "OTP-verification";
  public static final String OTP_VERIFICATION_REGISTER = BASE_API_PATH + "otpVerification";
  public static final String RESEND_OTP = BASE_API_PATH + "resendOtp";
  

  // fuel type
  public static final String FUELTYPE = RESOURCE_BASI_API + "fuelType";
//  public static final String FUEL_TYPE_PAGINATION = RESOURCE_BASI_API + "fuelTypePagination";
//  public static final String FUELTYPE_BY_ID = FUELTYPE + SLASH + ID;

  // Mileage History - corporate
  public static final String MILEAGE_HISTORY_CORPORATE = CORPORATE_BASE_API + "mileageHistory";

  // restaurant
  public static final String CHECK_RESTAURANT = BASE_API_PATH + "checkRestaurant" + SLASH + ID;
  public static final String RESTAURANT_ADMIN_SIGNUP = BASE_API_PATH + "restaurant" + SLASH + "admin";

  // branch
  public static final String BRANCH = BASE_API_PATH + "branch";
  public static final String RESTAURANT = BASE_API_PATH + "getRestaurant";
  public static final String BRANCH_BY_ID = BRANCH + SLASH + ID;
  public static final String CHECK_BRANCH = BASE_API_PATH + "checkBranch" + SLASH + ID;
  public static final String BRANCH_BY_RESTAURANT_ID = BRANCH + SLASH + ID;
  public static final String GET_BRANCH = BASE_API_PATH + "getBranch";
  public static final String BRANCH_BY_BRANCH_NAME = GET_BRANCH + SLASH + BRANCHNAME;
  public static final String RESTAURANT_NAME = BASE_API_PATH+"getrestaurantName" + SLASH + ID;
  public static final String RESTAURANT_BY_RESTAURANT_NAME = RESTAURANT + SLASH + RESTAURANT_NAME;
  public static final String BRANCH_NAME =BASE_API_PATH+"getbranchName" + SLASH + ID;
  
  
  // CSV
  public static final String CSV_DOWNLOAD = BASE_API_PATH + "csvDownload";
  public static final String CSV_UPLOAD = BASE_API_PATH + "csvUpload";

  // branch admin
  public static final String BRANCH_ADMIN = BRANCH + SLASH + "admin";
  public static final String BRANCH_ADMIN_REGISTER = BRANCH_ADMIN + SLASH + "register";
  public static final String BRANCH_ADMIN_BY_RESTAURANT = BRANCH + SLASH + "admin" + SLASH + ID;
  public static final String BRANCH_ADMIN_BY_ID = BRANCH + SLASH + "admin" + SLASH + ID;
  public static final String BRANCH_ADMIN_BY_BRANCHID = BASE_API_PATH + "branchadmin" + SLASH + ID;
  public static final String BRANCH_ADMIN_ALLOCATE = BRANCH + SLASH + "adminAllocate";

  // delivery boy
  public static final String DELIVERYBOY = BASE_API_PATH + "deliveryBoy";
  public static final String DELIVERYBOY_BY_RESTAURANTID = DELIVERYBOY + SLASH + "{restaurantId}";
  public static final String DELIVERYBOY_BY_ID = DELIVERYBOY + SLASH + ID;
  public static final String DELIVERYBOY_ALLOCATE = DELIVERYBOY + SLASH + "deliveryBoyAllocate";
  public static final String DELIVERYBOY_BY_BRANCHID_AND_RESTAURANTID =
		  DELIVERYBOY + SLASH + "{restaurantId}" + SLASH + "{branchId}";

  // notification
  public static final String NOTIFICATION = "notification";
  public static final String BRANCH_ADMIN_ALLOCATION_NOTIFICATION =
      BASE_API_PATH + "anytime-ate/websocket/branchAdminAllocation";
  public static final String DELIVERYBOY_ALLOCATION_NOTIFICATION =
      BASE_API_PATH + "anytime-ate/websocket/deliveryBoyAllocation";

  // ROLE_PERMISSION
  public static final String ROLE_PERMISSION = BASE_API_PATH + "role-permission";
  public static final String ROLE_PERMISSION_BY_ID = BASE_API_PATH + "role-permission" + SLASH + ID;

  // standalone Accident Document
  public static final String ACCIDENT_DOCUMENT_STANDALONE =
      STANDALONE_BASE_API + "accidentDocument";
  public static final String ACCIDENT_DOCUMENT_BY_ID_STANDALONE = STANDALONE_BASE_API + SLASH + ID;
  public static final String ACCIDENT_DOCUMENT_BY_VEHICLE_AND_USER_STANDALONE =
      STANDALONE_BASE_API + ACCIDENT_DOCUMENT_STANDALONE + "vehicleUser";

  // revenue document
  public static final String REVENUE_LICENSE_STANDALONE = STANDALONE_BASE_API + "revenueLicense";
  public static final String REVENUE_LICENSE_BY_USER_ID_STANDALONE =
      REVENUE_LICENSE_STANDALONE + SLASH + USER_ID;
  public static final String REVENUE_LICENSE_BY_ID_STANDALONE =
      REVENUE_LICENSE_STANDALONE + SLASH + ID;

  // emission test
  public static final String EMISSION_TEST_STANDALONE = STANDALONE_BASE_API + "emissionTest";
  public static final String EMISSION_TEST_BY_ID_STANDALONE = EMISSION_TEST_STANDALONE + SLASH + ID;

  // insurance
  public static final String INSURANCE_STANDALONE = STANDALONE_BASE_API + "insurance";
  public static final String INSURANCE_BY_ID_STANDALONE = INSURANCE_STANDALONE + SLASH + ID;

  // mileage History
  public static final String MILEAGE_HISTORY_STANDALONE = STANDALONE_BASE_API + "mileageHistory";
  public static final String MILEAGE_HISTORY_BY_ID_STANDALONE =
      MILEAGE_HISTORY_STANDALONE + SLASH + ID;
  public static final String MILEAGE_HISTORY_BY_VEHICLE_ID_STANDALONE =
      MILEAGE_HISTORY_STANDALONE + SLASH + "{vehicleId}";

  // part
  public static final String PART_STANDALONE = STANDALONE_BASE_API + "part";
  public static final String PART_BY_ID_STANDALONE = PART_STANDALONE + SLASH + ID;

  // parts
  public static final String PARTS_STANDALONE = STANDALONE_BASE_API + "parts";
  public static final String PARTS_BY_ID_PARTS_STANDALONE = PARTS_STANDALONE + SLASH + ID;
  public static final String PARTS_BY_VEHICLE_ID_PARTS_STANDALONE =
      PARTS_STANDALONE + SLASH + "vehicle" + SLASH + ID;
  public static final String PARTS_BY_VEHICLE_AND_PART_PARTS_STANDALONE =
      STANDALONE_BASE_API + "vehicleParts";
  public static final String PARTS_BY_DELIVERYBOY_PARTS_STANDALONE = STANDALONE_BASE_API + "deliveryBoyParts";
  public static final String PARTS_BY_VEHICLE_AND_PART_AND_USER_PARTS_STANDALONE =
      STANDALONE_BASE_API + "deliveryBoyVehicleParts";
  // fuel
  public static final String FUEL_UP_STANDALONE = STANDALONE_BASE_API + "fuelUp";

  // vehicle
  public static final String VEHICLE_BY_USER_ID_STANDALONE =
      STANDALONE_BASE_API + "vehicle" + SLASH + ID;
  public static final String USER_VEHICLE_STANDALONE = STANDALONE_BASE_API + "userVehicle";
  public static final String VEHICLE_BY_USER_ID_AND_VEHICLEID__STANDALONE =
      STANDALONE_BASE_API + "vehicle";

  // vehicleService
  public static final String VEHICLE_SERVICE__STANDALONE = STANDALONE_BASE_API + "vehicleService";
  public static final String VEHICLE_SERVICE_EXPIRY_BY_TIMELINE__STANDALONE =
      STANDALONE_BASE_API + "vehicleService" + SLASH + "timeline";
  public static final String SERVICE_WEBSOCKET__STANDALONE =
      STANDALONE_BASE_API + "servicessWebSocket";
  public static final String SERVICE_BY_VEHICLE_ID__STANDALONE =
      STANDALONE_BASE_API + "serviceByVehicle" + SLASH + ID;

  // expenses
  public static final String EXPENSES_STANDALONE = STANDALONE_BASE_API + "expenses";
  public static final String EXPENSES_BY_USER_ID_STANDALONE =
      STANDALONE_BASE_API + SLASH + "user" + ID;
  public static final String EXPENSES_BY_VEHICLE_ID_STANDALONE =
      STANDALONE_BASE_API + SLASH + "vehicle" + ID;


  // notificationPoints
  public static final String NOTIFICATION_POINTS_STANDALONE =
      STANDALONE_BASE_API + "notificationPoints";

  // notificationPReceipient
  public static final String NOTIFICATION_RECEIPIENT_STANDALONE =
      STANDALONE_BASE_API + "notificationReceipient";

  // generator
  public static final String GENERATOR_STANDALONE = STANDALONE_BASE_API + "generator";
  public static final String GENERATOR_BY_ID_STANDALONE =
      STANDALONE_BASE_API + "generator" + SLASH + ID;
  public static final String GENERATOR_OIL_HISTORY_BY_ID__STANDALONE =
      STANDALONE_BASE_API + "generatorHistory" + SLASH + "{generatorId}";
  public static final String GENERATOR_OPERATOR_STANDALONE =
      STANDALONE_BASE_API + "generatorOperator";
  public static final String GENERATOR_OIL_CHANGE_STANDALONE = STANDALONE_BASE_API + "generatorOil";

  // danger zone setting
  public static final String DANGER_ZONE_SETTING_STANDALONE =
      STANDALONE_BASE_API + "dangerZoneSetting";
  // partsWebsocket
  public static final String PARTS_WEBSOCKET_STANDALONE = STANDALONE_BASE_API + "partsWebSocket";

  // vehicle
//  public static final String restaurant_VEHICLE_CORPORATE = CORPORATE_BASE_API + "restaurantVehicle";
//  public static final String VEHICLE_BY_restaurant_ID_CORPORATE =
//      restaurant_VEHICLE_CORPORATE + SLASH + ID;
//  public static final String VEHICLE_CORPORATE = CORPORATE_BASE_API + "vehicle";
//  public static final String VEHICLE_BY_restaurant_ID_AND_BRANCH_ID_CORPORATE =
//      restaurant_VEHICLE_CORPORATE + SLASH + ID + SLASH + BRANCHID;
//  public static final String VEHICLE_NUMBER_restaurant_ID_CORPORATE =
//      CORPORATE_BASE_API + "vehiclerestaurant" + SLASH + VEHICLENUMBER + SLASH + restaurantID;
//  public static final String VEHICLE_BY_restaurantID_BRANCHID_USERID_VEHICLENUMBER_CORPORATE =
//      VEHICLE_CORPORATE + SLASH + restaurantID + SLASH + BRANCHID + SLASH + ID + SLASH + VEHICLENUMBER;
//  public static final String VEHICLE_BULK_UPLOAD_CORPORATE =
//      CORPORATE_BASE_API + "vehicleBulkUpload";
//  public static final String VEHICLE_COUNT_BY_restaurant_ID_CORPORATE =
//      CORPORATE_BASE_API + "vehicleCount" + SLASH + ID;
//  public static final String VEHICLE_CSV_DOWNLOAD_CORPORATE =
//      CORPORATE_BASE_API + "vehicleBulkDownload";

  // vehicle Allocation
//  public static final String VEHICLE_ALLOCATION_CORPORATE =
//      CORPORATE_BASE_API + "vehicleAllocation";
//  public static final String VEHICLE_ALLOCATION_BY_USER_CORPORATE =
//      CORPORATE_BASE_API + "vehicleAllocation" + SLASH + ID;

  // generator
//  public static final String GENERATOR_CORPORATE = CORPORATE_BASE_API + "generator";
//  public static final String GENERATOR_OIL_CHANGE_CORPORATE = CORPORATE_BASE_API + "generatorOil";
//  public static final String GENERATOR_BY_ID_CORPORATE =
//      CORPORATE_BASE_API + "generatorOilHistory" + SLASH + "{generatorId}";
//  public static final String UPDATE_GENERATOR_CORPORATE = CORPORATE_BASE_API + "updateGenerator";
//  public static final String GENERATORBY_ID_CORPORATE =
//      CORPORATE_BASE_API + "generator" + SLASH + ID;

  // emission test
//  public static final String EMISSION_TEST_CORPORATE = CORPORATE_BASE_API + "emissionTest";
//  public static final String EMISSION_TEST_BY_ID_CORPORATE = EMISSION_TEST_CORPORATE + SLASH + ID;
//  public static final String EMISSION_TEST_BYID = EMISSION_TEST_CORPORATE + SLASH + USER_ID;
//  public static final String EMISSION_TEST_BY_VEHICLENUMBER_CORPORATE =
//      EMISSION_TEST_CORPORATE + SLASH + VEHICLENUMBER;
//  public static final String EMISSIONTEST_BY_USERID_AND_VEHICLENUMBER_CORPORATE =
//      CORPORATE_BASE_API + "emissionTest" + SLASH + ID + SLASH + VEHICLENUMBER;

  // insurance
//  public static final String INSURANCE_CORPORATE = CORPORATE_BASE_API + "insurance";
//  public static final String INSURANCE_BY_ID_CORPORATE = INSURANCE_CORPORATE + SLASH + ID;
//  public static final String INSURANCE_BY_VEHICLENUMBER_CORPORATE =
//      INSURANCE_CORPORATE + SLASH + VEHICLENUMBER;
//  public static final String INSURANCE_BY_USERID_AND_VEHICLENUMBER_CORPORATE =
//      CORPORATE_BASE_API + "insurance" + SLASH + ID + SLASH + VEHICLENUMBER;

  // accident document
//  public static final String ACCIDENT_DOCUMENT_CORPORATE = CORPORATE_BASE_API + "accidentDocument";
//  public static final String ACCIDENT_DOCUMENT_BY_ID_CORPORATE =
//      ACCIDENT_DOCUMENT_CORPORATE + SLASH + ID;
//  public static final String ACCIDENT_DOCUMENT_BY_VEHICLENUMBER_CORPORATE =
//        ACCIDENT_DOCUMENT_CORPORATE + VEHICLENUMBER;
//  public static final String ACCIDENT_DOCUMENT_BY_VEHICLE_AND_USER_CORPORATE =
//      CORPORATE_BASE_API + ACCIDENT_DOCUMENT_CORPORATE + "vehicleUser";
  // parts
//  public static final String PARTS_CORPORATE = CORPORATE_BASE_API + "parts";
//  public static final String PARTS_BY_ID_CORPORATE = PARTS_CORPORATE + SLASH + ID;
//  public static final String PARTS_BY_VEHICLE_NUMBER_CORPORATE =
//      PARTS_CORPORATE + SLASH + "vehicle";
//  public static final String PARTS_BY_VEHICLE_AND_PART_CORPORATE =
//      CORPORATE_BASE_API + "vehicleParts";
//  public static final String PARTS_BY_CORPORATE_USER_CORPORATE =
//      CORPORATE_BASE_API + "corporateParts";
//
//  public static final String PARTS_BY_VEHICLE_AND_USER_CORPORATE =
//      CORPORATE_BASE_API + "vehicleParts" + SLASH + "vehicle" + SLASH + "{vehicleId}" + SLASH
//          + "user" + SLASH + "{userId}";
//  public static final String PART_BY_VEHICLE_AND_USER_CORPORATE =
//      PARTS_CORPORATE + SLASH + "vehicle" + SLASH + "{vehicleId}" + SLASH + "user" + SLASH
//          + "{userId}" + SLASH + "part" + SLASH + "{partId}";
//  public static final String PARTS_BY_VEHICLE_ID_CORPORATE =
//      PARTS_CORPORATE + SLASH + "vehicle" + SLASH + ID;
//  public static final String PARTS_BY_VEHICLE_AND_PART_AND_USER_CORPORATE =
//      CORPORATE_BASE_API + "corporateVehicleParts";
//  public static final String PART_BY_ID_WITHOUT_CONTENTRESPONSE_CORPORATE =
//      CORPORATE_BASE_API + "partWithoutContentResponse";

  // vehicleService
//  public static final String VEHICLE_SERVICE_CORPORATE = CORPORATE_BASE_API + "vehicleService";
//  public static final String SERVICE_BY_VEHICLE_NUMBER_CORPORATE =
//      CORPORATE_BASE_API + "vehicleService" + SLASH + VEHICLENUMBER;
//  public static final String SERVICE_BY_SERVICEID_AND_VEHICLE_NUMBER_CORPORATE =
//      CORPORATE_BASE_API + "vehicleService" + SLASH + ID + SLASH + VEHICLENUMBER;
//  public static final String SERVICE_CORPORATE = CORPORATE_BASE_API + "serviceById";
//
//  // revenue license
//  public static final String REVENUE_LICENSE_CORPORATE = CORPORATE_BASE_API + "revenueLicense";
//  public static final String GET_REVENUE_LICENSE_CORPORATE =
//      CORPORATE_BASE_API + "revenueLicense" + SLASH + VEHICLENUMBER + SLASH + ID;
//  public static final String REVENUE_LICENSE_BY_USER_ID_CORPORATE =
//      REVENUE_LICENSE_CORPORATE + SLASH + ID;
//  public static final String REVENUE_LICENSE_BY_ID_CORPORATE =
//      REVENUE_LICENSE_CORPORATE + SLASH + ID;



  // part
//  public static final String PART_CORPORATE = CORPORATE_BASE_API + "part";
//  public static final String PART_BY_ID_CORPORATE = PART_CORPORATE + SLASH + ID;
//
//  // fuel
//  public static final String FUEL_UP_CORPORATE = CORPORATE_BASE_API + "fuelUp";
//
//  // expenses
//  public static final String EXPENSES_CORPORATE = CORPORATE_BASE_API + "expenses";
//  public static final String EXPENSES_BY_USER_ID_CORPORATE =
//      CORPORATE_BASE_API + "user" + SLASH + ID;
//  public static final String EXPENSES_BY_VEHICLE_ID_CORPORATE =
//      CORPORATE_BASE_API + "vehicle" + SLASH + ID;



  // admin service
//  public static final String EXPENSES_BY_BRANCH_ID_CORPORATE =
//      EXPENSES_CORPORATE + SLASH + "branch" + SLASH + ID;
//  public static final String EXPENSES_BY_restaurant_ID_CORPORATE =
//      EXPENSES_CORPORATE + SLASH + "restaurant" + SLASH + ID;
//  public static final String EXPENSES_BY_VEHICLE_TYPE_ID_BRANCH_ID_CORPORATE =
//      EXPENSES_CORPORATE + SLASH + "{vehicleTypeId}" + SLASH + "{branchId}";
//  // notificationPoints
//  public static final String NOTIFICATION_POINTS_CORPORATE =
//      CORPORATE_BASE_API + "notificationPoints";
//
//  // notificationPReceipient
//  public static final String NOTIFICATION_RECEIPIENT_CORPORATE =
//      CORPORATE_BASE_API + "notificationReceipient";
//
//  // mileage History;
//  public static final String MILEAGE_HISTORY_BY_ID_CORPORATE =
//      MILEAGE_HISTORY_CORPORATE + SLASH + ID;
//  public static final String MILEAGE_HISTORY_BY_VEHICLE_ID_CORPORATE =
//      MILEAGE_HISTORY_CORPORATE + SLASH + VEHICLENUMBER;



  // notification
  public static final String NOTIFICATION_CORPORATE = CORPORATE_BASE_API + "notification";

  // danger zone setting
  public static final String DANGER_ZONE_SETTING_CORPORATE =
      CORPORATE_BASE_API + "dangerZoneSetting";



  // vehicle brand
  public static final String VEHICLE_BRAND_RESOURCE = RESOURCE_BASI_API + "vehicleBrand";
  public static final String VEHICLE_BRAND_WITHPAGINATION_RESOURCE =
      RESOURCE_BASI_API + "vehicleBrandWithPagination";
  public static final String VEHICLE_BRAND_BY_ID_RESOURCE =
      RESOURCE_BASI_API + "vehicleBrand" + SLASH + ID;

  // vehicle type
  public static final String VEHICLE_TYPE_RESOURCE = RESOURCE_BASI_API + "vehicleType";
  public static final String VEHICLE_TYPE_PAGINATION_RESOURCE =
      RESOURCE_BASI_API + "vehicleTypePagination";

  // fuel type
  public static final String FUELTYPE_RESOURCE = RESOURCE_BASI_API + "fuelType";
  public static final String FUEL_TYPE_PAGINATION_RESOURCE =
      RESOURCE_BASI_API + "fuelTypePagination";
  public static final String FUELTYPE_BY_ID_RESOURCE = FUELTYPE + SLASH + ID;

  // user
  public static final String CHANGE_PASSWORD_RESOURCE = BASE_API_PATH + "changePassword";
  public static final String FORGOTPASSWORD_RESOURCE = BASE_API_PATH + "email" + SLASH + "{email}";
  public static final String RESET_PASSWORD_RESOURCE = BASE_API_PATH + "forgot";

  public static final String VEHICLE_TYPE_BY_ID_RESOURCE =
      RESOURCE_BASI_API + "vehicleType" + SLASH + ID;

  // vehicle body type
  public static final String VEHICLE_BODY_TYPE_RESOURCE = RESOURCE_BASI_API + "vehicleBodyType";
  public static final String VEHICLE_BODY_TYPE_BY_ID_RESOURCE =
      RESOURCE_BASI_API + "vehicleBodyType" + SLASH + ID;


  // vehicle model
  public static final String VEHICLE_MODEL_RESOURCE = RESOURCE_BASI_API + "vehicleModel";
  public static final String VEHICLE_MODEL_BY_ID_RESOURCE =
      RESOURCE_BASI_API + "vehicleModel" + SLASH + ID;

  // transmission
  public static final String TRANMISSION_RESOURCE = RESOURCE_BASI_API + "transmission";
  public static final String TRANMISSION_BY_ID_RESOURCE =
      RESOURCE_BASI_API + "transmission" + SLASH + ID;

  // vehicle
  public static final String VEHICLE_RESOURCE = RESOURCE_BASI_API + "vehicle";
  public static final String VEHICLE_BY_ID_RESOURCE = RESOURCE_BASI_API + "vehicle" + SLASH + ID;
  public static final String VEHICLE_SEARCH_RESOURCE = RESOURCE_BASI_API + "vehicleSearch";
  public static final String VEHICLE_MODEL_SEARCH_RESOURCE =
      RESOURCE_BASI_API + "vehicleModelSearch";

  // part
  public static final String PART_RESOURCE = RESOURCE_BASI_API + "part";
  public static final String PART_BY_ID_RESOURCE = PART_RESOURCE + SLASH + ID;
  public static final String PART_BY_ID_WITHOUT_CONTENTRESPONSE_RESOURCE =
      RESOURCE_BASI_API + "partWithoutContentResponse" + SLASH + ID;
  // parts
  public static final String PARTS_RESOURCE = RESOURCE_BASI_API + "parts";
  public static final String PARTS_BY_ID_RESOURCE = PARTS_RESOURCE + SLASH + ID;
  public static final String PARTS_BY_VEHICLE_ID_RESOURCE =
      PARTS_RESOURCE + SLASH + "vehicle" + SLASH + ID;
  public static final String PARTS_BY_VEHICLE_AND_PART_RESOURCE =
      RESOURCE_BASI_API + "vehicleParts";
  public static final String PARTS_BY_DELIVERYBOY_RESOURCE = RESOURCE_BASI_API + "deliveryBoyParts";

  // service
  public static final String SERVICE_RESOURCE = RESOURCE_BASI_API + "service";
  public static final String SERVICE_BY_ID_RESOURCE = RESOURCE_BASI_API + "service" + SLASH + ID;
  public static final String SERVICE_BY_ID_WITHOUT_CONTENTRESPONSE_RESOURCE =
      RESOURCE_BASI_API + "serviceById" + SLASH + ID;
  public static final String CHECK_SERVICE_RESOURCE =
      RESOURCE_BASI_API + "checkService" + SLASH + ID;
  public static final String IS_PART_EXISTS_RESOURCE =
      RESOURCE_BASI_API + "partExists" + SLASH + ID;

  // dangerZoneSetting
  public static final String DANGER_ZONE_SETTING_RESOURCE = RESOURCE_BASI_API + "dangerZoneSetting";

  public static final String CHECK_VEHICELRESOURCE_CORPORATE = CORPORATE_BASE_API + "vehicleResource" + SLASH + ID;
  public static final String CHECK_PARTSRESOURCE_CORPORATE = CORPORATE_BASE_API + "partResource" + SLASH + ID;

  public static final String CHECK_VEHICELRESOURCE_STANDALONE = STANDALONE_BASE_API + "vehicleResource" + SLASH + ID;
  public static final String CHECK_PARTSRESOURCE_STANDALONE = STANDALONE_BASE_API + "partResource" + SLASH + ID;

}
