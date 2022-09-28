package it.itwtech.ateauth.service;

import it.itwtech.ateauth.dto.DeliveryBoyBranchUpdateDto;
import it.itwtech.ateauth.model.BranchAdmin;
import it.itwtech.ateauth.model.DeliveryBoy;
import it.itwtech.ateauth.model.User;

public interface MailNotificationsServices {
  public void sendTemporaryUserPasswordEmail(String email, User user, String password);

  public void forgotMail(String mail, String token, String origin);

  public void passwordResetSuccess(String mail);

  public void sendDeactivateStatusEmail(String email, User user);

  public void sendVerifyEmail(String email, String token);

  public void sendVerifyEmailCorporate(String email, String token);

  public void sendBranchAdminAllocation(String email, BranchAdmin branchAdmin);

  public void sendDeliveryBoyAllocationEmail(String email, DeliveryBoy deliveryBoy);
  
  public void sendAccountCreatedEmailForStandAlone(String mail);
  
  public void sendAccountCreatedEmailForCorporate(String mail);

public void sendDeliveryBoyBranchUpdateEmail(DeliveryBoyBranchUpdateDto deliveryBoyBranchUpdateDto);

}
