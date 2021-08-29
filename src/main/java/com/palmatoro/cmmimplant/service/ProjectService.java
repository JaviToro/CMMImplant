package com.palmatoro.cmmimplant.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.domain.Project.ProjectType;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;

    // Auxiliary Services ----------------------------------------
    @Autowired
    private UserService userService;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsAsList(){
        List<Project> projects = new ArrayList<>();
        for (Project project : projectRepository.findAll()) {
            projects.add(project);
        }
        return projects;
    }

    public Project getProjectByName(String name) {

        Project project;
        try {
            project = projectRepository.findByName(name);
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se ha encontrado un proyecto con el nombre " + name);
        }

        return project;
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
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado un proyecto con el ID: " + id));

        project.setName(name);
        project.setProjectType(projectType);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Integer id) throws Exception {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado un proyecto con el ID: " + id));
        if(getProjectByPrincipal().getId().equals(id)) {
            throw new Exception("No puedes eliminar el proyecto al que perteneces.");
        }
        projectRepository.delete(project);
    }

    // Auxiliary methods -------------------------------------------------------

    public Project getProjectByPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User u = userService.getUserByUsername(currentPrincipalName);

        return u.getProject();
    }
    
}
