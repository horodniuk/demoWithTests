package com.example.demowithtests.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeMailService extends EmployeeService {

    Set<String> sendEmailsAllUkrainian();

}
