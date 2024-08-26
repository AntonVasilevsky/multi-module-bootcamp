package com.bootcamp.web.controller;

import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;

import com.bootcamp.service.EmployeeServiceImpl;
import com.bootcamp.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Log4j2
public class ProjectController {
    private final ProjectService projectProjectService;

    public ProjectController(ProjectService projectProjectService) {
        this.projectProjectService = projectProjectService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addOne(
            @RequestBody @Valid ProjectDto dto
    ) {

        projectProjectService.add(dto);
        log.info("new project added\n{}", dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<String> addEmployeeToProjectById(
            @PathVariable int projectId,
            @PathVariable int employeeId
    ) {
        return projectProjectService.addEmployeeToProjectById(projectId, employeeId);
    }

    @GetMapping("/all")
    public List<ProjectDto> showAll() {
        return projectProjectService.showAllProjectsWithEmployees();
    }

    @GetMapping
    public Page<Project> showAllPage() {
        return projectProjectService.getPages();
    }



}
