package it.itwtech.ateauth.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.itwtech.ateauth.model.Branch;
import it.itwtech.ateauth.repositories.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService {

  @Autowired
  private BranchRepository branchRepository;

  @Override
  public boolean isBranchExist(Long id) {
    return branchRepository.existsById(id);
  }

  @Override
  public boolean isMailExist(String email) {
    return branchRepository.existsByEmail(email);
  }

  @Override
  public Branch addBranch(Branch branch) {
    return branchRepository.save(branch);
  }

  @Override
  public Branch updateBranch(Branch branch) {
    Branch branchDetails = branchRepository.save(branch);
    return branchDetails;
  }

  @Override
  public void deleteBranchById(Long id) {
    branchRepository.deleteById(id);
  }

  @Override
  public List<Branch> getBranchByRestaurantId(Long restaurantId) {
    return branchRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public boolean isMobileExists(String mobileNumber) {
    return branchRepository.existsByPhoneNumber(mobileNumber);
  }

}
