package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.EmployeeRepository;

import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.service.emailService.EmailSenderService;
import com.example.demowithtests.service.passport.PassportService;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.annotations.service.CheckEmployeeIsDeleted;
import com.example.demowithtests.util.exception.EmployeeNotFoundException;
import com.example.demowithtests.util.exception.EmployeeWasDeletedException;
import com.example.demowithtests.util.exception.GenderNotFoundException;
import com.example.demowithtests.util.history.passport.PassportHistory;
import com.example.demowithtests.util.history.passport.PassportMethodName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeCrudService,
        EmployeePaginationService,
        EmployeeFilterService,
        EmployeeSortService,
        EmployeeMailService,
        EmployeeGroupingService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final PassportService passportService;
    private final PassportRepository passportRepository;
    private final PassportHistory passportHistory;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

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
    @CheckEmployeeIsDeleted
    public void removeById(Integer id) {
        var employee = employeeRepository.findById(id).get();
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
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAllByIsDeletedFalse(pageable);
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
    public List<Employee> findByNameContaining(String name) {
        return null;
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
    public Set<String> sendEmailsAllUkrainian() {
        var ukrainians = employeeRepository.findAllUkrainian()
                .orElseThrow(() -> new EntityNotFoundException("Employees from Ukraine not found!"));
        var emails = new HashSet<String>();
        ukrainians.forEach(employee -> {
            emailSenderService.sendEmail(
                    /*employee.getEmail(),*/
                    "kaluzny.oleg@gmail.com", //для тесту
                    "Need to update your information",
                    String.format(
                            "Dear " + employee.getName() + "!\n" +
                            "\n" +
                            "The expiration date of your information is coming up soon. \n" +
                            "Please. Don't delay in updating it. \n" +
                            "\n" +
                            "Best regards,\n" +
                            "Ukrainian Info Service.")
            );
            emails.add(employee.getEmail());
        });

        return emails;
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

    @Override
    public Employee issuancePassport(Integer employeeId, Integer passportId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        Passport passport = passportService.handlePassport(passportId);
        employee.setPassport(passport);
        passportHistory.addPassportHistory(employeeId, passport, PassportMethodName.ISSUANCE_PASSPORT);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee cancelPassport(Integer employeeId) {
        Employee employee = getById(employeeId);
        Passport passport = employee.getPassport();
        if (passport != null) {
            passportService.cancelPassport(passport, employeeId);
            employee.setPassport(null);
            passportHistory.addPassportHistory(employeeId, passport, PassportMethodName.CANCEL_PASSPORT);
            return employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public List<Passport> findByCanceledEmployeeId(Integer employeeId) {
        return passportRepository.findByCanceledEmployeeId(employeeId);
    }


}