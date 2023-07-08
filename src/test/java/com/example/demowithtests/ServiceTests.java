package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.util.exception.EmployeeWasDeletedException;
import com.example.demowithtests.util.exception.GenderNotFoundException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class ServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceBean service;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Mark")
                .country("UK")
                .email("test@gmail.com")
                .gender(Gender.M)
                .build();
    }

    @Test
    @DisplayName("Save employee test")
    public void whenSaveEmployee_shouldReturnEmployee() {

        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        var created = service.create(employee);
        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Get employee by exist id test")
    public void whenGivenId_shouldReturnEmployee_ifFound() {

        Employee employee = new Employee();
        employee.setId(88);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {
        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

    @Test
    @DisplayName("Read employee by id test")
    public void readEmployeeByIdTest() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Read all employees test")
    public void readAllEmployeesTest() {

        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        var list = employeeRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Remove employee by id test")
    public void removeByIdTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        service.removeById(employee.getId());
        assertTrue(employee.isDeleted());
        verify(employeeRepository).save(employee);
    }


   /* @Test
    @DisplayName("Throw exception when remove employee but employe was deleted")
    public void removeByIdTestWithNotExistId() {
        employee.setDeleted(true);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        assertThrows(EmployeeWasDeletedException.class, () -> service.removeById(employee.getId()));
    }*/

    @Test
    @DisplayName("Update country first letter to upperCase test")
    public void updateCountryFirstLetterToUpperCaseTest() {
        employee.setCountry("ukraine");
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        when(employeeRepository.findByCountryFirstLetterLowerCase()).thenReturn(employees);
        service.updateCountryFirstLetterToUpperCase();
        assertEquals("Ukraine", employee.getCountry());
        verify(employeeRepository).saveAll(employees);
    }

    @Test
    @DisplayName("Update mail by id test")
    public void updateMailByIdTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        service.updateMailById(employee.getId(), "test_mail.gmail");
        assertEquals("test_mail.gmail", employee.getEmail());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Filter employees by country and 'gmail' email")
    public void filterByCountryAndGmailEmailTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        String country = "UK";

        when(employeeRepository.findByCountryAndEmailIsGmail(country)).thenReturn(employees);
        List<Employee> employeesTemp = service.filterByCountryAndGmailEmail(country);

        assertThat(employeesTemp).isEqualTo(employees);
        verify(employeeRepository).findByCountryAndEmailIsGmail(country);
    }

    @Test
    @DisplayName("Count employees by gender")
    public void countByGenderTest() {
        Gender gender = employee.getGender();

        when(employeeRepository.countByGender(gender)).thenReturn(1);
        Integer countTemp = service.countByGender(gender.name());

        assertThat(countTemp).isEqualTo(countTemp);
        verify(employeeRepository).countByGender(gender);
    }

}
