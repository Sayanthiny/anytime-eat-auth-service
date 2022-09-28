package it.itwtech.ateauth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.itwtech.ateauth.utils.EndPointURI;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public PasswordEncoder passwordEncoder;

  private static final String[] AUTH_WHITELIST =
      {"/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/oauth/token",
          "/api/v1/OTP-verification", "/api/v1/otpVerification","/api/v1/anytime-ate/oauth2/user/**"};

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
        .antMatchers(EndPointURI.FORGOTPASSWORD).permitAll().antMatchers(EndPointURI.RESET_PASSWORD)
        .permitAll().anyRequest().authenticated();

  }
}
