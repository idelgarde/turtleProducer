package com.testsurvey.testSurvey.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


  private static final String[] AUTH_WHITELIST = {
      // -- swagger ui
      "/v2/api-docs",
      "/v3/api-docs",
      "/swagger-resources/**",
      "/swagger-ui/**",
  };

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors().and().csrf().disable().authorizeRequests().
        antMatchers(AUTH_WHITELIST).permitAll().
        antMatchers("/**").permitAll();
  }

}