package com.nirmalya.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nirmalya.ppmtool.domain.User;
import com.nirmalya.ppmtool.exception.UserNameAlreadyExistsException;
import com.nirmalya.ppmtool.payload.JWTLoginSuccessResponse;
import com.nirmalya.ppmtool.payload.LoginRequest;
import com.nirmalya.ppmtool.security.JWTProvider;
import com.nirmalya.ppmtool.service.UserService;
import com.nirmalya.ppmtool.service.ValidationErrorService;
import com.nirmalya.ppmtool.utilities.SecurityConstant;
import com.nirmalya.ppmtool.validator.UserValidator;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

	private UserService userService;
	private ValidationErrorService validationErrorService;
	private UserValidator userValidator;
	private JWTProvider tokenProvider;
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);
		if (errorMap != null)
			return errorMap;

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword()
						)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstant.TOKEN_PREFIX +  tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		System.out.println(user.getConfirmPassword());
		userValidator.validate(user, result);

		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);
		if (errorMap != null)
			return errorMap;
		

		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

	}

}
