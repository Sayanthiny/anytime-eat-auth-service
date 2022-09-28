package it.itwtech.ateauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.itwtech.ateauth.model.OauthClientDetails;

@Repository
public interface ClientRepository extends JpaRepository<OauthClientDetails, String> {

	OauthClientDetails findByClientId(String clientId);

}
