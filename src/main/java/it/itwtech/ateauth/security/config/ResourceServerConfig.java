package it.itwtech.ateauth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.itwtech.ateauth.filter.AuthenticationFilter;
import it.itwtech.ateauth.security.error.ErrorAccessDeniedHandler;
import it.itwtech.ateauth.security.error.RestAuthEntryPoint;
import it.itwtech.ateauth.utils.EndPointURI;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Autowired
  private RestAuthEntryPoint restAuthEntryPoint;

  @Autowired
  private ErrorAccessDeniedHandler accessDeniedHandler;

  @Autowired
  private AuthenticationFilter authenticationFilter;

  private static final String RESOURCE_ID = "resource-server-rest-api";

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }

  private static final String[] AUTH_WHITELIST = {"/v2/api-docs", "/swagger-resources/**",
      "/swagger-ui.html", "/webjars/**", "/oauth/token", "/api/v1/checkuser/**",
      "/api/v1/checkCompany/**", "/api/v1/checkBranch/**", "/api/v1/OTP-verification",
      "/api/v1/otpVerification", "/api/v1/checkVehicle/**", "/api/v1/vehicleResource/**",
      "/api/v1/user/**", "/api/v1/branchadmin/**", "/api/v1/fleet-management/oauth2/user/**",
      "/api/v1/userpasswordReset/**", "/api/v1/resendOtp/**", "/api/v1/getBranch/**",
      "/api/v1/getCompany/**", "/api/v1/getbranchName/**", "/api/v1/getCompanyName/**"};

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors().and().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
        .antMatchers(EndPointURI.FORGOTPASSWORD).permitAll().antMatchers(EndPointURI.RESET_PASSWORD)
        .permitAll()
//        .antMatchers(HttpMethod.POST, EndPointURI.FUELTYPE)
//        .hasAuthority(PrivilegeConstants.CREATE_FUELTYPE)
//        .antMatchers(HttpMethod.GET, EndPointURI.FUEL_TYPE_PAGINATION)
//        .hasAuthority(PrivilegeConstants.VIEW_FUELTYPE)
//        .antMatchers(HttpMethod.POST, EndPointURI.MILEAGE_HISTORY_CORPORATE)
//        .hasAuthority(PrivilegeConstants.CREATE_MILEAGE_HISTORY)
//        // standalone accident document
//        .antMatchers(HttpMethod.POST, EndPointURI.ACCIDENT_DOCUMENT_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.ACCIDENT_DOCUMENT_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.ACCIDENT_DOCUMENT_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.ACCIDENT_DOCUMENT_BY_ID_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // stanadlone insurance document
//        .antMatchers(HttpMethod.POST, EndPointURI.INSURANCE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.INSURANCE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.INSURANCE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.INSURANCE_BY_ID_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // standalone emission test
//        .antMatchers(HttpMethod.POST, EndPointURI.EMISSION_TEST_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.EMISSION_TEST_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.EMISSION_TEST_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.EMISSION_TEST_BY_ID_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // generator Standalone
//        .antMatchers(HttpMethod.POST, EndPointURI.GENERATOR_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.GENERATOR_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.GENERATOR_OIL_CHANGE_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.GENERATOR_OPERATOR_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATOR_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATOR_BY_ID_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATOR_OIL_HISTORY_BY_ID__STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.GENERATOR_BY_ID_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN")
//        // dangerzone settings
//        .antMatchers(HttpMethod.POST, EndPointURI.DANGER_ZONE_SETTING_STANDALONE)
//        .hasAnyAuthority("SETTINGS_MAINTAIN", "SETTINGS_WRITE")
//        // mileage
//        .antMatchers(HttpMethod.POST, EndPointURI.MILEAGE_HISTORY_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.MILEAGE_HISTORY_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.MILEAGE_HISTORY_BY_VEHICLE_ID_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.MILEAGE_HISTORY_BY_ID_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN")
//        // expenses
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_USER_ID_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_VEHICLE_ID_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//
//        // standalone revenue license
//        .antMatchers(HttpMethod.POST, EndPointURI.REVENUE_LICENSE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.REVENUE_LICENSE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.REVENUE_LICENSE_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.REVENUE_LICENSE_BY_ID_STANDALONE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // parts standalone
//        .antMatchers(HttpMethod.POST, EndPointURI.PARTS_BY_DRIVER_PARTS_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.PARTS_BY_DRIVER_PARTS_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.PARTS_BY_VEHICLE_ID_PARTS_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.PARTS_BY_VEHICLE_AND_PART_PARTS_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.GET,
//            EndPointURI.PARTS_BY_VEHICLE_AND_PART_AND_USER_PARTS_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//
//        // part standalone
//        .antMatchers(HttpMethod.POST, EndPointURI.PART_STANDALONE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.PART_STANDALONE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.PART_BY_ID_STANDALONE)
//        .hasAnyAuthority("PART_MAINTAIN").antMatchers(HttpMethod.GET, EndPointURI.PART_STANDALONE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE", "PART_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.PART_BY_ID_STANDALONE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE", "PART_READ")
//        // vehicle standalone
//        .antMatchers(HttpMethod.POST, EndPointURI.USER_VEHICLE_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.USER_VEHICLE_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BY_USER_ID_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BY_USER_ID_AND_VEHICLEID__STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//
//        // vehicle service standalone
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_SERVICE__STANDALONE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_SERVICE__STANDALONE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.SERVICE_BY_VEHICLE_ID__STANDALONE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE",
//            "VEHICLE_SERVICE_READ")
//
//        // corporate accident document
//        .antMatchers(HttpMethod.POST, EndPointURI.ACCIDENT_DOCUMENT_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.ACCIDENT_DOCUMENT_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.ACCIDENT_DOCUMENT_BY_VEHICLENUMBER_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.ACCIDENT_DOCUMENT_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//
//        // corporate emission test
//        .antMatchers(HttpMethod.POST, EndPointURI.EMISSION_TEST_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.EMISSION_TEST_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.EMISSION_TEST_BYID)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EMISSION_TEST_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.EMISSION_TEST_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // dangerzone settings corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.DANGER_ZONE_SETTING_CORPORATE)
//        .hasAnyAuthority("SETTINGS_MAINTAIN", "SETTINGS_WRITE")
//        // expenses corporate
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_USER_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_BRANCH_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_COMPANY_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_VEHICLE_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.EXPENSES_BY_VEHICLE_TYPE_ID_BRANCH_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//
//        // generator corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.GENERATOR_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.GENERATOR_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.GENERATOR_OIL_CHANGE_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.UPDATE_GENERATOR_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATOR_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATOR_BY_ID_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.GENERATORBY_ID_CORPORATE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN", "GENERATOR_WRITE", "GENERATOR_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.GENERATOR_BY_ID_STANDALONE)
//        .hasAnyAuthority("GENERATOR_MAINTAIN")
//        // mileage corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.MILEAGE_HISTORY_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.MILEAGE_HISTORY_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.MILEAGE_HISTORY_BY_VEHICLE_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.MILEAGE_HISTORY_BY_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN")
//        // notification
//        .antMatchers(HttpMethod.POST, EndPointURI.NOTIFICATION_CORPORATE)
//        .hasAnyAuthority("NOTIFICATION_MAINTAIN", "NOTIFICATION_WRITE")
//        // corporate revenue license
//        .antMatchers(HttpMethod.POST, EndPointURI.INSURANCE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.INSURANCE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.INSURANCE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.INSURANCE_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // corporate insurance
//        .antMatchers(HttpMethod.POST, EndPointURI.REVENUE_LICENSE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.REVENUE_LICENSE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.REVENUE_LICENSE_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.REVENUE_LICENSE_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//        // parts corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.PARTS_BY_CORPORATE_USER_CORPORATE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.PARTS_BY_CORPORATE_USER_CORPORATE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.PARTS_BY_VEHICLE_NUMBER_CORPORATE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.PARTS_BY_VEHICLE_AND_PART_CORPORATE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.PARTS_BY_ID_CORPORATE)
//        .hasAnyAuthority("PART_MAINTAIN")
//
//        // part corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.PART_CORPORATE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.PART_CORPORATE)
//        .hasAnyAuthority("PART_MAINTAIN", "PART_WRITE")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.PART_BY_ID_CORPORATE)
//        .hasAnyAuthority("PART_MAINTAIN")
//        // vehicle corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.COMPANY_VEHICLE_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_BULK_UPLOAD_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.USER_VEHICLE_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BY_COMPANY_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_CSV_DOWNLOAD_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BY_COMPANY_ID_AND_BRANCH_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_NUMBER_COMPANY_ID_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_BRAND_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_BRAND_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BRAND_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BRAND_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_BRAND_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_TYPE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_TYPE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN")
//
//
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_MODEL_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_MODEL_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_MODEL_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_MODEL_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_MODEL_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_MODEL_SEARCH_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_SEARCH_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_BODY_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.VEHICLE_BODY_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.VEHICLE_BODY_TYPE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BODY_TYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.VEHICLE_BODY_TYPE_BY_ID_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.FUEL_UP_CORPORATE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.FUEL_UP_STANDALONE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.FUELTYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.FUEL_TYPE_PAGINATION_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.FUELTYPE_BY_ID)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.FUELTYPE_RESOURCE)
//        .hasAnyAuthority("VEHICLE_RESOURCE_MAINTAIN", "VEHICLE_RESOURCE_WRITE",
//            "VEHICLE_RESOURCE_READ")
//        // vehicle service corporate
//        .antMatchers(HttpMethod.POST, EndPointURI.VEHICLE_SERVICE_CORPORATE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.SERVICE_BY_VEHICLE_NUMBER_CORPORATE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE",
//            "VEHICLE_SERVICE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.SERVICE_BY_SERVICEID_AND_VEHICLE_NUMBER_CORPORATE)
//        .hasAnyAuthority("VEHICLE_SERVICE_MAINTAIN", "VEHICLE_SERVICE_WRITE",
//            "VEHICLE_SERVICE_READ")
//
//        .antMatchers(HttpMethod.POST, EndPointURI.COMPANY_ADMIN_SIGNUP)
//        .hasAnyAuthority("COMPANY_ADMIN_MAINTAIN", "COMPANY_ADMIN_WRITE")
//
//
//        // branch
//        .antMatchers(HttpMethod.POST, EndPointURI.BRANCH)
//        .hasAnyAuthority("COMPANY_BRANCH_MAINTAIN", "COMPANY_BRANCH_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.BRANCH)
//        .hasAnyAuthority("COMPANY_BRANCH_MAINTAIN", "COMPANY_BRANCH_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.BRANCH_BY_COMPANY_ID)
//        .hasAnyAuthority("COMPANY_BRANCH_MAINTAIN", "COMPANY_BRANCH_WRITE", "COMPANY_BRANCH_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.BRANCH_ADMIN_BY_COMPANY)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN", "COMPANY_BRANCH_ADMIN_WRITE",
//            "COMPANY_BRANCH_ADMIN_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.BRANCH_ADMIN_BY_ID)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN")
//        .antMatchers(HttpMethod.PUT, EndPointURI.BRANCH_ADMIN)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN", "COMPANY_BRANCH_ADMIN_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.BRANCH_ADMIN_BY_BRANCHID)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN", "COMPANY_BRANCH_ADMIN_WRITE",
//            "COMPANY_BRANCH_ADMIN_READ")
//        .antMatchers(HttpMethod.PUT, EndPointURI.BRANCH_ADMIN_ALLOCATE)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN", "COMPANY_BRANCH_ADMIN_WRITE")
//        .antMatchers(HttpMethod.POST, EndPointURI.BRANCH_ADMIN_REGISTER)
//        .hasAnyAuthority("COMPANY_BRANCH_ADMIN_MAINTAIN", "COMPANY_BRANCH_ADMIN_WRITE")
//
//        // driver
//        .antMatchers(HttpMethod.GET, EndPointURI.DRIVER_BY_COMPANYID)
//        .hasAnyAuthority("DRIVER_MAINTAIN", "DRIVER_WRITE", "DRIVER_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.DRIVER_BY_BRANCHID_AND_COMPANYID)
//        .hasAnyAuthority("DRIVER_MAINTAIN", "DRIVER_WRITE", "DRIVER_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.DRIVER_BY_ID).hasAnyAuthority("DRIVER_MAINTAIN")
//        .antMatchers(HttpMethod.PUT, EndPointURI.DRIVER)
//        .hasAnyAuthority("DRIVER_MAINTAIN", "DRIVER_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.DRIVER_ALLOCATE)
//        .hasAnyAuthority("DRIVER_MAINTAIN", "DRIVER_WRITE")
//        .antMatchers(HttpMethod.POST, EndPointURI.DRIVER)
//        .hasAnyAuthority("DRIVER_MAINTAIN", "DRIVER_WRITE")
//
//        .antMatchers(HttpMethod.PUT, EndPointURI.EMISSION_TEST_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.EMISSION_TEST_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN", "DOCUMENT_WRITE", "DOCUMENT_READ")
//        .antMatchers(HttpMethod.DELETE, EndPointURI.EMISSION_TEST_BY_ID_CORPORATE)
//        .hasAnyAuthority("DOCUMENT_MAINTAIN")
//
//        .antMatchers(HttpMethod.GET, EndPointURI.CHECK_PARTSRESOURCE_STANDALONE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.CHECK_PARTSRESOURCE_CORPORATE)
//        .hasAnyAuthority("PARTS_MAINTAIN", "PARTS_WRITE", "PARTS_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.CHECK_VEHICELRESOURCE_STANDALONE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//        .antMatchers(HttpMethod.GET, EndPointURI.CHECK_VEHICELRESOURCE_CORPORATE)
//        .hasAnyAuthority("VEHICLE_MAINTAIN", "VEHICLE_WRITE", "VEHICLE_READ")
//
//
//
//        // privilege
//        .antMatchers(HttpMethod.POST, EndPointURI.ROLE_PERMISSION)
//        .hasAnyAuthority("PRIVILEGE_MAINTAIN", "PRIVILEGE_WRITE")
//        .antMatchers(HttpMethod.PUT, EndPointURI.ROLE_PERMISSION)
//        .hasAnyAuthority("PRIVILEGE_MAINTAIN", "PRIVILEGE_WRITE")
//        .antMatchers(HttpMethod.GET, EndPointURI.ROLE_PERMISSION_BY_ID)
//        .hasAnyAuthority("PRIVILEGE_MAINTAIN", "PRIVILEGE_WRITE", "PRIVILEGE_READ")
        .anyRequest().authenticated();


    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    http.exceptionHandling().authenticationEntryPoint(restAuthEntryPoint);
    http.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
