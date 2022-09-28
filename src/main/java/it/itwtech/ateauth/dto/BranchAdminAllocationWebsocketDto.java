package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchAdminAllocationWebsocketDto {
  private Long id;
  private String shortmsg;
  private String detailsmsg;
  private String createdAt;
  private String updatedAt;
  private Long userId;

}
