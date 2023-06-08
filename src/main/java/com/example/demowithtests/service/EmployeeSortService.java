package com.example.demowithtests.service;

import java.util.List;
import java.util.Optional;

public interface EmployeeSortService extends EmployeeService {


    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
     List<String> getAllEmployeeCountry();


    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
     List<String> getSortCountry();

     Optional<String> findEmails();
}
