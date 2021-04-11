package com.nirmalya.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirmalya.ppmtool.domain.BackLog;
import com.nirmalya.ppmtool.domain.Project;
import com.nirmalya.ppmtool.domain.User;
import com.nirmalya.ppmtool.exception.ProjectIdException;
import com.nirmalya.ppmtool.exception.ProjectNotFoundException;
import com.nirmalya.ppmtool.repository.BackLogRepository;
import com.nirmalya.ppmtool.repository.ProjectRepository;
import com.nirmalya.ppmtool.repository.UserRepository;

@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	private BackLogRepository backlogRepository;
	private UserRepository userRepository;

	public ProjectService(ProjectRepository projectRepository, BackLogRepository backlogRepository,
			UserRepository userRepository) {
		super();
		this.projectRepository = projectRepository;
		this.backlogRepository = backlogRepository;
		this.userRepository = userRepository;
	}

	public Project saveOrUpdateProject(Project project, String userName) {
		 searchProjectByProjectLeader(project, userName);
		
		try {
			User user = userRepository.findByUsername(userName);

			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			if (project.getId() == null) {
				BackLog backlog = new BackLog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			} else {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}

	public Project findProjectByIdentifier(String Id, String userName) {
		Project project = projectRepository.findByProjectIdentifier(Id.toUpperCase());

		if (project == null) {
			throw new ProjectNotFoundException("Project Id " + Id + " does not exist");
		}
		if (!project.getProjectLeader().equals(userName)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String userName) {
		return projectRepository.findAllByProjectLeader(userName);
	}

	public void deleteProjectByIdentifier(String projectId, String userName) {
		projectRepository.delete(findProjectByIdentifier(projectId, userName));
	}
	
	public Project searchProjectByProjectLeader(Project project, String userName) {
		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if (existingProject != null && (!existingProject.getProjectLeader().equals(userName))) {
				throw new ProjectNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}
		return project;
	}

}
