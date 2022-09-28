package it.itwtech.ateauth.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

	NEW("New"), ACTIVE("Active"), INACTIVE("Inactive");

	private String userStatus;

	private UserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public static UserStatus getUserStatus(String userStatus) {
		for (UserStatus status : values()) {
			if (status.userStatus.equals(userStatus)) {
				return status;
			}
		}
		throw new AssertionError("UserType not found for the [Type: " + userStatus + "]");
	}
}
