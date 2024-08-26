package com.bootcamp.service;

import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService extends Service<Project>{
     Project add(ProjectDto dto);
     ResponseEntity<String> addEmployeeToProjectById(int projectId, int employeeId);
     List<ProjectDto> showAllProjectsWithEmployees();
}
