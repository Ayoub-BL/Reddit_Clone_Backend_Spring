package com.ayoubbl.codingbeans.redditclonebackendspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ayoubbl.codingbeans.redditclonebackendspring.exceptions.RedditCloneException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity.csrf().disable()
					.authorizeRequests()
					.antMatchers("/api/auth/**")
					.permitAll()
					.anyRequest()
					.authenticated();
		} catch (Exception e) {
			String msg = "Exception occurred at " + SecurityConfig.class.getSimpleName() + ".configure() method!";
			log.error(msg, e);
			throw new RedditCloneException(msg);
		}
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {
		try {
			authenticationManagerBuilder.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			String msg = "Exception occurred at " + SecurityConfig.class.getSimpleName() 
					+ ".configureGlobal() method!";
			log.error(msg, e);
			throw new RedditCloneException(msg, e);
		}
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() {
		try {
			return super.authenticationManagerBean();
		} catch (Exception e) {
			String msg = "Exception occurred at " + SecurityConfig.class.getSimpleName() 
					+ ".authenticationManagerBean() method!";
			log.error(msg, e);
			throw new RedditCloneException(msg);
		}
	}
	
}