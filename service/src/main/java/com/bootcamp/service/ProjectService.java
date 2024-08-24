package com.bootcamp.service;

import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findByName(String name);

    List<Project> findAll();

    void deleteOne(Project p);

    Project add(ProjectDto dto);

    void deleteAll();

    Optional<Project> findById(int projectId);

    Page<Project> getPages();

    @Transactional
    ResponseEntity<String> addEmployeeToProjectById(int projectId, int employeeId);

    List<ProjectDto> showAllProjectsWithEmployees();
}
