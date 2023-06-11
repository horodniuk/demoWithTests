package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.List;

public interface EmployeeFilterService extends EmployeeService {

    List<Employee> filterByCountry(String country);

    List<Employee> filterByEmailIsNull();

}
