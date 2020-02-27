package com.springBoot;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//Authentication : Users -Roles
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  	// enable in memory based authentication with a user named
		  // this method taken from docs .. replace &quat: with "
		  	auth.inMemoryAuthentication().withUser("user1").password("secret").roles("USER").and()
		  			.withUser("admin1").password("secret").roles("USER", "ADMIN");
		  }
	//Authorization : user -> Access
	  protected void configure(HttpSecurity http) throws Exception {
// authorize Link Means that anything which has a survey you need to have user access 
			http.httpBasic().and()
				.authorizeRequests()
				.antMatchers("/survey/**").hasRole("USER")
				.antMatchers("/users/**").hasRole("USER")
				.antMatchers("/**").hasRole("ADMIN")
				.and().csrf().disable()
				.headers().frameOptions().disable();
				
					
		}
}
