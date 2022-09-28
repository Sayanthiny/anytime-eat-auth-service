package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.itwtech.ateauth.enums.UserStatus;
import it.itwtech.ateauth.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User extends DateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER")
  @SequenceGenerator(name = "USER", sequenceName = "SEQ_FM_USER", allocationSize = 1)
  private Long id;
  private String firstName;
  private String lastName;
  private String nic;
  private String mobileNumber;
  private String subscription;
  private String email;
  private String password;
  private Boolean status;

  private String drivingLicenseNo;
  @ManyToOne
  @JoinColumn(name = "drivingLicenseTypeId")
  private DrivingLicenseType drivingLicenseType;
  @ManyToOne
  @JoinColumn(name = "roleId")
  private Role role;
  @Enumerated(EnumType.STRING)
  UserStatus userStatus;
  @Enumerated(EnumType.STRING)
  UserType userType;
}
