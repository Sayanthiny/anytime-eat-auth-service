package it.itwtech.ateauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableZuulProxy
@EnableJpaAuditing
@EnableAsync
@EnableEurekaClient
public class AteAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AteAuthApplication.class, args);
	}
	 @Bean
	  public PasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	
	@Bean
	  public RestTemplate restTemplate() {
	    return new RestTemplate();
	  }

}
