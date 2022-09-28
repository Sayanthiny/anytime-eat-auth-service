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
@Table(name = "permission")
@Getter
@Setter
public class Permission extends DateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION")
  @SequenceGenerator(name = "PERMISSION", sequenceName = "SEQ_DT_PERMISSION", allocationSize = 1)
  private long id;
  private String name;
  private String description;
}
