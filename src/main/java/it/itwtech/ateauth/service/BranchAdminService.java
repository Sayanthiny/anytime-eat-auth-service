package it.itwtech.ateauth.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import it.itwtech.ateauth.dto.BranchAdminAllocateDto;
import it.itwtech.ateauth.dto.BranchAdminResponseDto;
import it.itwtech.ateauth.dto.BranchUserDto;
import it.itwtech.ateauth.model.BranchAdmin;

public interface BranchAdminService {

  public boolean isBranchAdminExists(Long id);

  public void deleteBranchAdmin(Long id);

  public void updateBranchAdmin(@Valid BranchUserDto branchUserDto, HttpServletRequest request);

  public Long getBranchAdminByBranchId(Long branchId);

  public String allocateBranchAdmin(@Valid BranchAdminAllocateDto branchAdminAllocateDto);
  
  public boolean isBranchAdminUserExists(Long id);

  public BranchAdmin findRestaurantBranchAdminById(Long id);

  public List<BranchAdminResponseDto> getAllBranchAdminByRestaurant(Long id);

}
