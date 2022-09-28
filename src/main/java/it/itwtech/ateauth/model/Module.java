package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "module")
@Getter
@Setter
public class Module extends DateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULE_ID")
  @SequenceGenerator(name = "MODULE_ID", sequenceName = "SEQ_FM_MODULE", allocationSize = 1)
  private Long id;
  private String name;
  private String prefix;
  private String permissionName;
}
