package it.itwtech.ateauth.security.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.itwtech.ateauth.enums.UserRoles;
import it.itwtech.ateauth.enums.UserType;
import it.itwtech.ateauth.model.BranchAdmin;
import it.itwtech.ateauth.model.DeliveryBoy;
import it.itwtech.ateauth.model.RestaurantAdmin;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.service.BranchAdminService;
import it.itwtech.ateauth.service.DeliveryBoyService;
import it.itwtech.ateauth.service.RestaurantAdminService;
import it.itwtech.ateauth.service.UserService;


public class AccessTokenConverter extends JwtAccessTokenConverter {

  @Autowired
  private UserService userService;

  @Autowired
  private BranchAdminService branchAdminService;

  @Autowired
  private DeliveryBoyService deliveryBoyService;

  @Autowired
  private RestaurantAdminService restaurantAdminService;

  public static final String ERROR = "error";
  public static final String DESCRIPTION = "error_description";
  public static final String ACCESS_DENIED = "access_denied";

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    if (authentication.getOAuth2Request().getGrantType().equalsIgnoreCase("password")) {
      final Map<String, Object> additionalInfo = new HashMap<String, Object>();
      User user = userService.findByMail(authentication.getName());

      // add basic info
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      Long userId = user.getId();
      String restaurantName = "";
      String restaurantBranchName = "";
      Long restaurantBranchId = null;
      Long restaurantId = null;

      // add role id
      Long roleId = user.getRole().getId();
      
      if (user.getUserType() != null) {
        if (user.getUserType().equals(UserType.RESTAURANTDELIVERYBOY)) {
          DeliveryBoy deliveryBoy = deliveryBoyService.findByUserId(user.getId());
          if (deliveryBoy != null) {
            restaurantName = deliveryBoy.getRestaurant().getRestaurantName();
            restaurantBranchId = deliveryBoy.getBranch().getId();
            restaurantId = deliveryBoy.getRestaurant().getId();
            restaurantBranchName = deliveryBoy.getBranch().getBranchName();
          }
        } else if (user.getUserType().equals(UserType.RESTAURANTADMIN)) {
          RestaurantAdmin restaurantAdmin = restaurantAdminService.findRestaurantAdminById(user.getId());
          if (restaurantAdmin != null) {
            restaurantName = restaurantAdmin.getRestaurant().getRestaurantName();
            restaurantId = restaurantAdmin.getRestaurant().getId();
          }
        } else if (user.getUserType().equals(UserType.RESTAURANTBRANCHADMIN)) {
          BranchAdmin branchAdmin = branchAdminService.findRestaurantBranchAdminById(user.getId());
          if (branchAdmin != null) {
            restaurantName = branchAdmin.getRestaurant().getRestaurantName();
            restaurantBranchId = branchAdmin.getBranch().getId();
            restaurantId = branchAdmin.getRestaurant().getId();
            restaurantBranchName = branchAdmin.getBranch().getBranchName();
          }
        }
      }

      additionalInfo.put("firstName", firstName);
      additionalInfo.put("lastName", lastName);
      additionalInfo.put("roleId", roleId);
      additionalInfo.put("userId", userId);
      additionalInfo.put("restaurantName", restaurantName);
      additionalInfo.put("restaurantId", restaurantId);
      additionalInfo.put("restaurantBranchName", restaurantBranchName);
      additionalInfo.put("restaurantBranchId", restaurantBranchId);

      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      String originType = request.getHeader("originType");
      boolean isNotAdmin = user.getRole().getRoleName().equals(UserRoles.STANDALONEUSER);

      if (originType != null && originType.equalsIgnoreCase("web") && isNotAdmin) {
        Map<String, String> errorParams = new HashMap<>();
        errorParams.put(ERROR, ACCESS_DENIED);
        errorParams.put(DESCRIPTION, "User don't have permisson for web");
        throw OAuth2Exception.valueOf(errorParams);
      }
      accessToken = super.enhance(accessToken, authentication);
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
    }
    return accessToken;
  }

}
