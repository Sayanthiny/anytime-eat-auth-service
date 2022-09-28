package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationReceipientResponseDto {
	private boolean isEnabled;
	private Long userId;
	private String email;
	private Long notificationPointId;
}
