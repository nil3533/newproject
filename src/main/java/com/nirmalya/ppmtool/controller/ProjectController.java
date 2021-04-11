package com.nirmalya.ppmtool.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nirmalya.ppmtool.domain.Project;
import com.nirmalya.ppmtool.service.ProjectService;
import com.nirmalya.ppmtool.service.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	private ProjectService projectService;
	private ValidationErrorService validationErrorService;
	
	
	public ProjectController(ProjectService projectService, ValidationErrorService validationErrorService) {
		super();
		this.projectService = projectService;
		this.validationErrorService = validationErrorService;
	}

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,Principal principal){		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);
		
		if(errorMap!=null)
			return errorMap;		 
		
		Project newProject = projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(newProject,HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){        
		Project project = projectService.findProjectByIdentifier(projectId,principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
	
	 @GetMapping("/all")
		public Iterable<Project> getAllProjects(Principal principal) {
			return projectService.findAllProjects(principal.getName());
		}
	 
	 @DeleteMapping("/{projectId}")
	    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
	        projectService.deleteProjectByIdentifier(projectId, principal.getName());
	        return new ResponseEntity<String>("Project "+projectId+" deleted successfully", HttpStatus.OK);
	    }
	}
