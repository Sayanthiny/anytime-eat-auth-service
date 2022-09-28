package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
  
  private Long id;
  private String message;
  private String description;
  private boolean isRead;
  private Long userId;

}
