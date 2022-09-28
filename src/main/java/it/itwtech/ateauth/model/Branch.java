package it.itwtech.ateauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRANCH")
	@SequenceGenerator(name = "BRANCH", sequenceName = "SEQ_FM_BRANCH", allocationSize = 1)
	private Long id;
	private String branchName;
	private String address;
	private String phoneNumber;
	private String email;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

}
