package com.bootcamp.web.controller;



import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Log4j2
@ComponentScan("com.bootcamp.service")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(
            @RequestBody @Valid EmployeeDto dto
    ) {
        // todo unique check
        employeeService.save(employeeService.add(dto));
        log.info("new employee added\n{}", dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @GetMapping("/all")
    public List<EmployeeDto>getAll() {
        return employeeService.getAllDtos();
    }



}
