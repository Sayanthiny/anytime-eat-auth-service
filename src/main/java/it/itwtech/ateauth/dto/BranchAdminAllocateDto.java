package it.itwtech.ateauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchAdminAllocateDto {
  
  private Long userId;
  private Long branchId;
  private Long restaurantId;
  private String shortmsg;
  private String detailsmsg;
  private String createdAt;
  private String updatedAt;
 
}
