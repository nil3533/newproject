package com.nirmalya.ppmtool.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirmalya.ppmtool.domain.User;
import com.nirmalya.ppmtool.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {

	private final String invalidUserMessage = "Invalid user login data";

	private UserRepository userRepository;

	public CustomUserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException(this.invalidUserMessage);
		}
		return user;
	}

	@Transactional
	public User loadUserById(Long id) {
		User user = userRepository.getById(id);
		if (user == null)
			new UsernameNotFoundException("User not found");
		return user;

	}

}
