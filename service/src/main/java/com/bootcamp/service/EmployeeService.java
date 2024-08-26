package com.bootcamp.service;

import com.bootcamp.domain.Employee;
import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.dto.SimpleEmployeeDto;

import java.util.List;

public interface EmployeeService extends Service<Employee> {
    Employee add(EmployeeDto dto);

    SimpleEmployeeDto toSimpleDto(Employee employee);

    List getAllDtos();
}
