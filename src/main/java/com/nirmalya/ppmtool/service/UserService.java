package com.nirmalya.ppmtool.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nirmalya.ppmtool.domain.User;
import com.nirmalya.ppmtool.exception.UserNameAlreadyExistsException;
import com.nirmalya.ppmtool.repository.UserRepository;
import com.nirmalya.ppmtool.validator.UserValidator;


@Service
public class UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;	
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User saveUser(User newUser) {
		try {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		newUser.setPassword(newUser.getPassword());
		newUser.setConfirmPassword("");
		return userRepository.save(newUser);
		}catch(Exception e) {
			throw new UserNameAlreadyExistsException("Usename " +newUser.getUsername()+" is exists.");
		}
	}

}
