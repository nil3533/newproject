package com.nirmalya.ppmtool.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nirmalya.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentifierOrderByStatusDesc(String id);

	List<ProjectTask> findByProjectIdentifierAndStatusOrderByProjectIdentifierDescProjectSequenceDesc(String id,String status);

	ProjectTask findByProjectSequence(String sequence);
}
