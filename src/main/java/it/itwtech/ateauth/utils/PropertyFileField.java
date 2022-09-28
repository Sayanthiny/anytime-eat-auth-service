package it.itwtech.ateauth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@PropertySource("classpath:application.properties")
public class PropertyFileField {
  
  @Value("${user.verify.token.expiray.time}")
  private String verifyTokenExpire;

  private PropertyFileField() {}

  @Value("${anytimeeat.standalone.port}")
  private String anytimeeatStandalonePort;
  @Value("${anytimeeat.standalone.server}")
  private String anytimeeatStandaloneServer;
  @Value("${anytimeeat.standalone.endpoint}")
  private String anytimeeatStandaloneEndPoint;

  @Value("${anytimeeat.corporate.port}")
  private String anytimeeatCorporatePort;
  @Value("${anytimeeat.corporate.server}")
  private String anytimeeatCorporateServer;
  @Value("${anytimeeat.corporate.endpoint}")
  private String anytimeeatCorporateEndPoint;

  // SMS Notification
  @Value("${notify.app.userid}")
  private String notifyAppUserId;
  @Value("${notify.app.apikey}")
  private String notifyAppApiKey;
  @Value("${notify.app.senderid}")
  private String notifyAppSenderId;
  
  // Web Socket
  @Value("${web.socket.port}")
  private Long webSocketPort;
  @Value("${web.socket.server}")
  private String webSocketServer;
  @Value("${web.socket.endpoint}")
  private String webSocketEndPoint;
}
