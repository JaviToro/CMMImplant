package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.Project.ProjectType;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Integer id){
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No project found on ID: " + id));
        return project;
    }

    @Transactional
    public Project addNewProject(Project project){
        return projectRepository.save(project);
    }

    @Transactional
    public Project editProject(Integer id, String name, ProjectType projectType, Date startDate, Date endDate){
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No project found on ID: " + id));

        project.setName(name);
        project.setProjectType(projectType);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Integer id){
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No project found on ID: " + id));
        projectRepository.delete(project);
    }
    
}
