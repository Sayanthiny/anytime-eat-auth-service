package it.itwtech.ateauth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * Specifying custom messages
 *
 */
@Component
@PropertySource("classpath:MessagesAndCodes.properties")
@Getter
@Setter
public class StatusCodeBundle {
	// Common Success code
	@Value("${code.success.common}")
	private String commonSuccessCode;

	@Value("${code.validation.authentication.accessDenied}")
	private String accessDeniedCode;

	@Value("${message.validation.authentication.accessDenied}")
	private String accessDeniedMessage;
}
