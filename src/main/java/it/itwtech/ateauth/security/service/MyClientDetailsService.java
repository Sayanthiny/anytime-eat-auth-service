package it.itwtech.ateauth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.itwtech.ateauth.details.MyClientDetails;
import it.itwtech.ateauth.model.OauthClientDetails;
import it.itwtech.ateauth.repositories.ClientRepository;


@Service
public class MyClientDetailsService implements ClientDetailsService {

  @Autowired
  private ClientRepository clientRepository;

  @Transactional
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    OauthClientDetails oauthClientDetails = clientRepository.findByClientId(clientId);
    if (oauthClientDetails == null)
      throw new ClientRegistrationException("client with " + clientId + " is not available");
    return new MyClientDetails(oauthClientDetails);
  }
}
