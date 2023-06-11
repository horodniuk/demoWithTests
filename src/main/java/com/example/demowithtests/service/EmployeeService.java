package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee create(Employee employee);

    Employee createEM(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    List<String> getAllEmployeeCountry();

    List<String> getSortCountry();

    Optional<String> findEmails();

    List<Employee> filterByCountry(String country);

    List<Employee> filterByEmailIsNull();

    List<Employee> updateCountryFirstLetterToUpperCase();




}
