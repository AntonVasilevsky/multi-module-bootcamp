package com.bootcamp.service;


import com.bootcamp.domain.Employee;
import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.dto.SimpleEmployeeDto;
import com.bootcamp.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService, DtoConverter<Employee, EmployeeDto> {
    private final EmployeeRepository repository;
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Employee> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Employee add(EmployeeDto dto) {
        return repository.save(toEntity(dto));
    }

    @Override
    public void deleteOne(Employee e) {
        repository.delete(e);
    }

    @Override
    public Optional<Employee> findById(int projectId) {
        return repository.findById(projectId);
    }

    @Override
    public Page<Employee> getPages() {
        PageRequest page = PageRequest.of(0, 10, Sort.by("name"));
        return repository.findAll(page);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Employee toEntity(EmployeeDto dto) {
        return modelMapper.map(dto, Employee.class);
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<Employee> manyToEntity(List<EmployeeDto> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public List<EmployeeDto> manyToDto(List<Employee> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .toList();
    }
    @Override
    public SimpleEmployeeDto toSimpleDto(Employee employee) {
        return modelMapper.map(employee, SimpleEmployeeDto.class);
    }
    @Override
    public List getAllDtos() {
        return manyToDto(findAll());
    }

}
