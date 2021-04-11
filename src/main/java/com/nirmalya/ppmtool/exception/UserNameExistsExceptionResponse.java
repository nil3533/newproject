package com.nirmalya.ppmtool.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNameExistsExceptionResponse {
	
	private String username;

}
