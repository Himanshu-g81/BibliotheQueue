package com.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bookstore.domain.User;
import com.bookstore.repository.UserRepository;

@Component
public class UserSecurityService implements UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username);
		
		if(null == user)
			throw new UsernameNotFoundException("User name not fount");

		return user;
	}
	
}