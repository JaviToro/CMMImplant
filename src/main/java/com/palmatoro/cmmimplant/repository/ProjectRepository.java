package com.palmatoro.cmmimplant.repository;

import com.palmatoro.cmmimplant.domain.Project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @Query("select p from Project p where p.name = ?1")
    Project findByName(String name);

}
