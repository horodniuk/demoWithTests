package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {

    Employee create(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    List<String> getAllEmployeeCountry();

    List<String> getSortCountry();

    Optional<String> findEmails();

    List<Employee> filterByCountry(String country);

    Set<String> sendEmailsAllUkrainian();

    List<Employee> findByNameContaining(String name);

    List<Employee> filterByEmailIsNull();

    List<Employee> updateCountryFirstLetterToUpperCase();

    Employee updateMailById(Integer id, String newMail);

    List<Employee> filterByCountryAndGmailEmail(String country);

    Integer countByGender(String gender);
}
