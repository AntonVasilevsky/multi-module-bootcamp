package com.bootcamp.service;


import com.bootcamp.domain.Employee;
import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.dto.SimpleEmployeeDto;
import com.bootcamp.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService implements DtoConverter<Employee, EmployeeDto> {
    private final EmployeeRepository repository;
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeService(EmployeeRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    public Optional<Employee> findByName(String name) {
        return repository.findByName(name);
    }
    public List<Employee> findAll() {
        return repository.findAll();
    }
    public void deleteOne(Employee e) {
        repository.delete(e);
    }
    public void add(Employee e) {
        repository.save(e);
    }

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

    public Optional<Employee> findById(int employeeId) {
        return repository.findById(employeeId);
    }
    public SimpleEmployeeDto toSimpleDto(Employee employee) {
        return modelMapper.map(employee, SimpleEmployeeDto.class);
    }

}
