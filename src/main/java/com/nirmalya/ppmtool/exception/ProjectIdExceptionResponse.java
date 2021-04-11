package com.nirmalya.ppmtool.exception;

import lombok.Data;

@Data
public class ProjectIdExceptionResponse {


	private String projectIdentifier;

	public ProjectIdExceptionResponse(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}
	
	
}
