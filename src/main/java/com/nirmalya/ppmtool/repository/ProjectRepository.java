package com.nirmalya.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;

import com.nirmalya.ppmtool.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>{

	Project findByProjectIdentifier(String projectid);
	
    Iterable<Project> findAll();
    
    Iterable<Project> findAllByProjectLeader(String username);

}
