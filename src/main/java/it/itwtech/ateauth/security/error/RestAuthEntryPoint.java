package it.itwtech.ateauth.security.error;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.itwtech.ateauth.enums.RestApiResponseStatus;
import it.itwtech.ateauth.responses.BasicResponse;
import it.itwtech.ateauth.utils.Constants;

@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.getWriter().write(mapper
				.writeValueAsString(new BasicResponse<>(RestApiResponseStatus.ERROR, Constants.TOKEN_NOT_PRESENT)));
	}
}
