package it.itwtech.ateauth.service;

import javax.validation.Valid;

import it.itwtech.ateauth.dto.BranchAdminAllocateDto;
import it.itwtech.ateauth.dto.BranchAdminUserDto;
import it.itwtech.ateauth.dto.DeliveryBoyAllocateDto;
import it.itwtech.ateauth.dto.DeliveryBoyDto;
import it.itwtech.ateauth.dto.OtpResendDto;
import it.itwtech.ateauth.dto.RestaurantAdminUserDto;
import it.itwtech.ateauth.dto.UserDto;
import it.itwtech.ateauth.model.User;
import it.itwtech.ateauth.model.VerificationToken;

public interface TextNotificationService {

  public void sendOTPforStandAlone(UserDto userDto, String verificationToken);

  public void sendTemporaryPassword(User user, String password);

  public void sendOTPforCorporate(RestaurantAdminUserDto userDto, String verificationToken);

  public void sendOTPforBranchAdminCreation(BranchAdminUserDto branchAdminUserDto,
      String verificationToken);

  public void sendOTPforDeliveryBoyCreation(DeliveryBoyDto deliveryBoyDto, String verificationToken);

  public void sendBranchAdminAllocationTextNotification(String oldBranch,
      BranchAdminAllocateDto branchAdminAllocateDto);

  public void sendDeliveryBoyAllocationTextNotification(@Valid DeliveryBoyAllocateDto deliveryBoyAllocateDto);

  public void sendOtpResendTextNotification(VerificationToken verificationToken,
      @Valid OtpResendDto otpResendDto);

  public void sendOTPforDeliveryBoyCreationBulkImport(User user, VerificationToken verificationToken);


}
