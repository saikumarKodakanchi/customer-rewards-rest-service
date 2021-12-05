package com.charter.customer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration of each end point.
 * All application endpoints should be authenticated with basic auth
 * @author saiku
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Configuring HTTP authentication for API endpoints 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();
		http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();
	}
}
