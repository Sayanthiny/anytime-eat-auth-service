package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import it.itwtech.ateauth.enums.Permissions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_permission")
public class RolePermission {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLEPERMISSION")
  @SequenceGenerator(name = "ROLEPERMISSION", sequenceName = "SEQ_FM_ROLEPERMISSION", allocationSize = 1)
  private Long id;
  private boolean status;
  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
  @JoinColumn(name = "permission", nullable = false)
  private Permissions permission;

  @ManyToOne
  @JoinColumn(name = "module_id", nullable = false)
  private Module module;
  private boolean contains;
}
