package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.exception.EmployeeNotFoundException;
import com.example.demowithtests.util.exception.EmployeeWasDeletedException;
import com.example.demowithtests.util.exception.GenderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeCrudService, EmployeePaginationService, EmployeeFilterService, EmployeeSortService, EmployeeGroupingService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
      if (employee.isDeleted()) {
            throw new EmployeeWasDeletedException();
        }
        return employee;
    }

    @Override
    public void removeById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employee.isDeleted()) {
            throw new EmployeeWasDeletedException();
        }
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    @Override
    public void removeAll() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            employee.setDeleted(true);
            employeeRepository.save(employee);
        }
    }


    @Override
    public Employee updateById(Integer id, Employee employee) {
        final var entity = employeeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        entity.setName(employee.getName());
        entity.setEmail(employee.getEmail());
        entity.setCountry(employee.getCountry());
        return employeeRepository.save(entity);
    }

    @Override
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEM(Employee employee) {
        return entityManager.merge(employee);
    }


    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAllByIsDeletedFalse(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }


   /* public boolean isValid(Employee employee) {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(employee.getPhone());
        boolean isFound = matcher.find();
        if (isFound) {
            System.out.println("Number is valid");
            return true;
        } else {
            System.out.println("Number is invalid");
            return false;
        }
    }*/

    /*public boolean isVodafone(Employee employee) {
        String regex = "^[0][9][5]{1}[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(employee.getPhone());
        boolean isFound = matcher.find();
        if (isFound) {
            System.out.println("Number is Vodafone");
            return true;
        } else {
            System.out.println("Number is not Vodafone");
            return false;
        }
    }*/

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAllByIsDeletedFalse();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAllByIsDeletedFalse();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("X"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAllByIsDeletedFalse();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findByCountry(country);
    }

    @Override
    public List<Employee> filterByEmailIsNull() {
        return employeeRepository.findByIsDeletedFalseAndEmailIsNull();
    }

    @Override
    public List<Employee> updateCountryFirstLetterToUpperCase() {
        List<Employee> employees = employeeRepository.findByCountryFirstLetterLowerCase();

        if (employees.isEmpty()) {
            return Collections.emptyList();
        }

        StringBuilder sb = new StringBuilder();

        for (Employee employee : employees) {
            sb.setLength(0);
            String country = employee.getCountry();
            sb.append(Character.toUpperCase(country.charAt(0))).append(country.substring(1));
            employee.setCountry(sb.toString());
        }

        return employeeRepository.saveAll(employees);
    }

    @Override
    public Employee updateMailById(Integer id, String newMail) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmail(newMail);
            return employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public List<Employee> filterByCountryAndGmailEmail(String country) {
        return employeeRepository.findByCountryAndEmailIsGmail(country);
    }

    @Override
    public Integer countByGender(String gender) {
        checkGenderValid(gender);
        return employeeRepository.countByGender(Gender.valueOf(gender));
    }

    private void checkGenderValid(String gender) {
        try {
            Gender.valueOf(gender);
        } catch (IllegalArgumentException ex) {
            throw new GenderNotFoundException(gender);
        }
    }
}