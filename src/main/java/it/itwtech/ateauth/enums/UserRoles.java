package it.itwtech.ateauth.enums;

public enum UserRoles {
	ADMIN("ADMIN"), RESTAURANTADMIN("RESTAURANTADMIN"), RESTAURANTBRANCHADMIN("RESTAURANTBRANCHADMIN"),
	RESTAURANTDELIVERYBOY("RESTAURANTDELIVERYBOY"),
	STANDALONEUSER("STANDALONEUSER");

	private String userRole;

	private UserRoles(String userRole) {
		this.userRole = userRole;
	}

	public static UserRoles getUserRole(String userRole) {
		for (UserRoles role : values()) {
			if (role.userRole.equals(userRole)) {
				return role;
			}
		}
		throw new AssertionError("UserRole not found for the [Role: " + userRole + "]");
	}
}
