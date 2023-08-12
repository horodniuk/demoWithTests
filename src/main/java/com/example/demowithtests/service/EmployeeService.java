package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    Employee updateMailById(Integer id, String newMail);

    List<Employee> filterByCountryAndGmailEmail(String country);

    Integer countByGender(String gender);

    Set<String> sendEmailsAllUkrainian();

    Employee issuancePassport(Integer employeeId, Integer passportId);

    Employee cancelPassport(Integer employeeId);

    List<Passport> findByCanceledEmployeeId(Integer employeeId);

}
