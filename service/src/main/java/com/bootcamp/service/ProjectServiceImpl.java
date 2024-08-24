package com.bootcamp.service;

import com.bootcamp.domain.Employee;
import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;
import com.bootcamp.dto.SimpleEmployeeDto;
import com.bootcamp.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService,  DtoConverter<Project, ProjectDto> {
    private final ProjectRepository repository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository repository, EmployeeService employeeService, ModelMapper modelMapper) {
        this.repository = repository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }
    @Override
    public Optional<Project> findByName(String name) {
        return repository.findByName(name);
    }
    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }
    @Override
    public void deleteOne(Project p) {
        repository.delete(p);
    }
    @Override
    public Project add(ProjectDto dto) {
       return repository.save(toEntity(dto));
    }
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Project toEntity(ProjectDto dto) { return modelMapper.map(dto, Project.class); }

    @Override
    public ProjectDto toDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public List<Project> manyToEntity(List<ProjectDto> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public List<ProjectDto> manyToDto(List<Project> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .toList();
    }
    @Override
    public Optional<Project> findById(int projectId) {
       return repository.findById(projectId);
    }
    @Transactional
    public void addEmployeeToProject(Project project, Employee employee) {
        employee.getProjects().add(project);
        project.getEmployees().add(employee);
        repository.save(project);
        employeeService.add(employee);
    }
    @Override
    public Page<Project> getPages() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        return repository.findAll(pageable);
    }
    @Override
    @Transactional
    public ResponseEntity<String> addEmployeeToProjectById(int projectId, int employeeId) {
        Optional<Project> pOptional = findById(projectId);
        if (pOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no project with id:%d".formatted(projectId));
        }
        Optional<Employee> eOptional = employeeService.findById(employeeId);
        if (eOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no employee with id:%d".formatted(employeeId));
        }
        addEmployeeToProject(pOptional.orElseThrow(), eOptional.orElseThrow());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @Override
    public List<ProjectDto> showAllProjectsWithEmployees() {
        return findAll().stream()
                .map(project -> {
                    ProjectDto projectDto = toDto(project);
                    List<SimpleEmployeeDto> simpleEmployeeDtoList = project.getEmployees().stream()
                            .map(employeeService::toSimpleDto)
                            .toList();
                    projectDto.setEmployees(simpleEmployeeDtoList);

                    return projectDto;
                })
                .filter(p -> p.getEmployees().size() > 0) // если я правильно понял Endpoint для получения всех проектов с сотрудниками:
                .sorted(Comparator.comparing(ProjectDto::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
