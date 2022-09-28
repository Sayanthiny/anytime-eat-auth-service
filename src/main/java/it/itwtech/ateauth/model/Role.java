package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.itwtech.ateauth.enums.UserRoles;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE")
	@SequenceGenerator(name = "ROLE", sequenceName = "SEQ_FM_ROLE", allocationSize = 1)
	private Long id;
	private UserRoles roleName;
}
