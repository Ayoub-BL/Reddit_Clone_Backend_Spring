package com.ayoubbl.codingbeans.redditclonebackendspring.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayoubbl.codingbeans.redditclonebackendspring.model.AppUser;
import com.ayoubbl.codingbeans.redditclonebackendspring.repository.AppUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final AppUserRepository appUserRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
		if (optionalAppUser.isPresent()) {
			AppUser appUser = optionalAppUser.get();
			try {
				return new User(
							appUser.getUsername(),
							appUser.getPassword(),
							appUser.isEnabled(),
							true,
							true,
							true,
							getAuthorities("USER"));
			} catch (IllegalArgumentException e) {
				String msg = "Exception occurred at " + UserDetailsServiceImpl.class.getSimpleName() 
						+ ".loadUserByUsername() method!"
						+ " IllegalArgumentException - a null value was passed either as a parameter "
						+ "or as an element in the GrantedAuthority collection!";
				log.error(msg, e);
				throw new UsernameNotFoundException(msg);
			}
		} else {
			String msg = "Exception occurred at " + UserDetailsServiceImpl.class.getSimpleName() 
					+ ".loadUserByUsername() method!"
					+ " No user found with username : \"" + username + "\"!";
			log.error(msg);
			throw new UsernameNotFoundException(msg);
		}
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return singletonList(new SimpleGrantedAuthority(role));
	}
	
}