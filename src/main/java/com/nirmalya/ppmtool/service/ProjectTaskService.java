package com.nirmalya.ppmtool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirmalya.ppmtool.domain.BackLog;
import com.nirmalya.ppmtool.domain.Project;
import com.nirmalya.ppmtool.domain.ProjectTask;
import com.nirmalya.ppmtool.exception.ProjectIdException;
import com.nirmalya.ppmtool.exception.ProjectNotFoundException;
import com.nirmalya.ppmtool.exception.RandomException;
import com.nirmalya.ppmtool.repository.BackLogRepository;
import com.nirmalya.ppmtool.repository.ProjectRepository;
import com.nirmalya.ppmtool.repository.ProjectTaskRepository;
import com.nirmalya.ppmtool.utilities.IConstant;

import static com.nirmalya.ppmtool.utilities.IConstant.PROJECT_TASK_HIGH_PRIORITY;

@Service
public class ProjectTaskService {

	private BackLogRepository backlogRepository;
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectService projectService;

	public ProjectTaskService(BackLogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {
		super();
		this.backlogRepository = backlogRepository;
		this.projectTaskRepository = projectTaskRepository;
	}

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		System.out.println(projectTask.getStatus() + projectTask.getPriority());

		Optional<BackLog> backLog = Optional
				.ofNullable(projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog());

		if (backLog.isPresent()) {
			System.out.println(123);
			BackLog aBackLog = backLog.get();
			projectTask.setBacklog(aBackLog);
			Integer backLogSequence = aBackLog.getPTSequence();
			backLogSequence++;
			aBackLog.setPTSequence(backLogSequence);
			projectTask.setProjectSequence(aBackLog.getProjectIdentifier() + "-" + backLogSequence);
			projectTask.setProjectIdentifier(aBackLog.getProjectIdentifier());
			System.out.println(backLogSequence);
		}
		if (projectTask.getStatus() == null || "".equals(projectTask.getStatus())) {
			projectTask.setStatus(IConstant.PROJECT_TASK_INITITAL);
		}
		if (projectTask.getPriority() == null || projectTask.getPriority() == IConstant.PROJECT_TASK_NO_PRIORITY) {
			projectTask.setPriority(PROJECT_TASK_HIGH_PRIORITY);
		}

		return projectTaskRepository.save(projectTask);

	}

	public List<ProjectTask> findBacklogDetailsByProjectIdentifier(String projectIdentifier, String userName) {

		projectService.findProjectByIdentifier(projectIdentifier, userName);

		return projectTaskRepository.findByProjectIdentifierOrderByStatusDesc(projectIdentifier);
	}

	public List<ProjectTask> findProjectBackLogDetailsByStatus(String projectIdentifier, String project_status) {
		// TODO Auto-generated method stub
		return projectTaskRepository.findByProjectIdentifierAndStatusOrderByProjectIdentifierDescProjectSequenceDesc(
				projectIdentifier, project_status);
	}

	public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String projectTaskSequence,
			String userName) {
		projectService.findProjectByIdentifier(projectIdentifier, userName);
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTaskSequence);

		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task sequence'" + projectTaskSequence + "' not found");
		}
		if (!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
			throw new ProjectNotFoundException(
					"Project Task Sequence " + projectTaskSequence + " is not found in Project " + projectIdentifier);
		}
		return projectTask;
	}

	public ProjectTask updateProjectTaskBySequence(ProjectTask projectTask, String projectIdentifier,
			String projectTaskSequence, String userName) {

		ProjectTask getAProjectTask = findProjectTaskByProjectSequence(projectIdentifier, projectTaskSequence,
				userName);
		getAProjectTask = projectTask;

		return projectTaskRepository.save(getAProjectTask);
	}

	public void deleteProjectTaskByProjectSequence(String projectIdentifier, String projectTaskSequence,
			String userName) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, projectTaskSequence, userName);

		projectTaskRepository.delete(projectTask);
	}

}
