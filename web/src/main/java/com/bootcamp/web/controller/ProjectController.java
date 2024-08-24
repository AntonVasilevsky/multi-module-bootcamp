package com.bootcamp.web.controller;

import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;
import com.bootcamp.service.EmployeeService;
import com.bootcamp.service.ProjectServiceImpl;

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
    private final ProjectServiceImpl projectServiceImpl;
    private final EmployeeService employeeService;

    public ProjectController(
            ProjectServiceImpl projectServiceImpl,
            EmployeeService employeeService
    ) {
        this.projectServiceImpl = projectServiceImpl;
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOne(
            @RequestBody @Valid ProjectDto dto
    ) {

        projectServiceImpl.add(dto);
        log.info("new project added\n{}", dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<String> addEmployeeToProjectById(
            @PathVariable int projectId,
            @PathVariable int employeeId
    ) {
        return projectServiceImpl.addEmployeeToProjectById(projectId, employeeId);
    }

    @GetMapping("/all")
    public List<ProjectDto> showAll() {
        return projectServiceImpl.showAllProjectsWithEmployees();
    }

    @GetMapping
    public Page<Project> showAllPage() {
        return projectServiceImpl.getPages();
    }

    //todo провертить transactional, вынести сервис в интерфейс

}
