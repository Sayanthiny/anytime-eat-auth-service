package it.itwtech.ateauth.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString 
public class NotificationReceipientsResponseDto {
	private List<NotificationPointsResponseDto> notificationPoints;
	private Long userId;
}
