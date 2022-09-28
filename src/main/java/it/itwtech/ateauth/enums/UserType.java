package it.itwtech.ateauth.enums;

public enum UserType {
  ADMIN("ADMIN"), RESTAURANTDELIVERYBOY("RestaurantDeliveryBoy"), STANDALONEUSER("StandaloneUser"), RESTAURANTADMIN(
      "RestaurantAdmin"), RESTAURANTBRANCHADMIN("RestaurantBranchAdmin");

  private String userType;

  private UserType(String userType) {
    this.userType = userType;
  }

  public static UserType getUserType(String userType) {
    for (UserType type : values()) {
      if (type.userType.equals(userType)) {
        return type;
      }
    }
    throw new AssertionError("UserType not found for the [Type: " + userType + "]");
  }
}
