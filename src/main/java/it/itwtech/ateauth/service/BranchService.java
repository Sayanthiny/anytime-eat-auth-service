package it.itwtech.ateauth.service;

import java.util.List;

import it.itwtech.ateauth.model.Branch;

public interface BranchService {

  public boolean isBranchExist(Long id);

  public boolean isMailExist(String email);

  public Branch addBranch(Branch branch);

  public Branch updateBranch(Branch branch);

  public void deleteBranchById(Long id);

  public List<Branch> getBranchByRestaurantId(Long companyId);

  public boolean isMobileExists(String mobileNumber);

}
