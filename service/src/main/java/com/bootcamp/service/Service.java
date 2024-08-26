package com.bootcamp.service;

import com.bootcamp.domain.Employee;
import com.bootcamp.domain.Project;
import com.bootcamp.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    Optional<T> findByName(String name);

    List<T> findAll();

     T save(T t);

    void deleteOne(T e);

    void deleteAll();

    Optional<T> findById(int projectId);

    Page<T> getPages();


}
