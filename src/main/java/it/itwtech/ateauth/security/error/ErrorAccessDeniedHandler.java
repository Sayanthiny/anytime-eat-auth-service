package it.itwtech.ateauth.security.error;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.itwtech.ateauth.enums.RequestStatus;
import it.itwtech.ateauth.responses.BaseResponse;
import it.itwtech.ateauth.utils.StatusCodeBundle;

@Component
public class ErrorAccessDeniedHandler implements AccessDeniedHandler {
  @Autowired
  private StatusCodeBundle statusCodeBundle;
  private static final Logger logger = LoggerFactory.getLogger(ErrorAccessDeniedHandler.class);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType("application/json");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    logger.warn("Access Denied for url:'{}'",request.getRequestURI());
    response.getWriter()
        .write(mapper.writeValueAsString(new BaseResponse(RequestStatus.ERROR.getStatus(),
            statusCodeBundle.getAccessDeniedCode(),
            statusCodeBundle.getAccessDeniedMessage())));
  }
}
