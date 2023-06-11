package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.List;


public interface EmployeeCrudService extends EmployeeService {

    Employee create(Employee employee);

    Employee createEM(Employee employee);

    Employee getById(Integer id);

    void removeById(Integer id);

    void removeAll();

    List<Employee> getAll();

    Employee updateById(Integer id, Employee plane);

    List<Employee> updateCountryFirstLetterToUpperCase();
}
