package com.ty.ToDo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.ToDo.model.User;
import com.ty.ToDo.repository.UserRepository;

import jakarta.validation.Valid;
@Service
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    return userRepository.findByUsername(username);
	}


	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public void save(@Valid User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
		
	}
	
	public User findByUsername(String username) {
	    return userRepository.findByUsername(username);
	}


	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
