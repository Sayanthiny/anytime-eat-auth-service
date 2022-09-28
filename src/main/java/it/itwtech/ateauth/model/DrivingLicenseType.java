package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "driving_license_type")
public class DrivingLicenseType extends DateAudit{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DRIVINGLICENCETYPE")
  @SequenceGenerator(name = "DRIVINGLICENCETYPE", sequenceName = "SEQ_FM_DRIVINGLICENCETYPE",
      allocationSize = 1)
  private Long id;
  private String type;

}
