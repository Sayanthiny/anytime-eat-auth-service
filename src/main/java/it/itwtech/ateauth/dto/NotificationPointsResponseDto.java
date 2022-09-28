package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationPointsResponseDto {
	private Long id;
	private String name;
	private String messageTemplate;
	private String descriptionTemplate;
	private boolean isEnabled;
	private Long notifcationCategoryId;

}
