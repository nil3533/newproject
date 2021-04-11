package com.nirmalya.ppmtool.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3127936587210625145L;

	public ProjectIdException(String message) {
        super(message);
    }
}
