package com.example.demowithtests.web.impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import com.example.demowithtests.dto.EmployeeUpdateMailReadDto;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.dto.passport.PassportReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.passport.image.ImageService;
import com.example.demowithtests.util.converter.EmployeeConverter;
import com.example.demowithtests.util.converter.PassportConverter;
import com.example.demowithtests.util.exception.EmployeePaginationException;
import com.example.demowithtests.web.EmployeeController;
import com.example.demowithtests.web.swagger.EmployeeControllerSwagger;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
public class EmployeeControllerBean implements EmployeeController, EmployeeControllerSwagger {

    private final ImageService imageService;
    private final EmployeeService employeeService;
    private final EmployeeConverter converter;
    private final PassportConverter passportConverter;

    @Override
    public EmployeeDto saveEmployee( EmployeeDto requestForSave) {
        var employee = converter.toEmployee(requestForSave);
        return converter.toDto(employeeService.create(employee));
    }

    @Override
    public void saveEmployee1(EmployeeDto employeeDto) {
        final var employee = EmployeeConverter.INSTANCE.toEmployee(employeeDto);
        employeeService.create(employee);
    }

    @Override
    public List<EmployeeDto> getAllUsers() {
        var employees = employeeService.getAll();
        return converter.toDtoList(employees);
    }

    @Override
    public Page<EmployeeDto> getPage(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new EmployeePaginationException("Parameters incorect. Check page and size value", page, size);
        }

        Pageable paging = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getAllWithPagination(paging);
        return employeePage.map(converter::toDto);
    }


    @Override
    public EmployeeReadDto getEmployeeById(Integer id) {
        var employee = employeeService.getById(id);
        return converter.toReadDto(employee);
    }

    @Override
    public EmployeeDto refreshEmployee(Integer id, EmployeeDto employeeDto) {
        Employee employee = converter.toEmployee(employeeDto);
        Employee updatedEmployee = employeeService.updateById(id, employee);
        return converter.toDto(updatedEmployee);
    }

    @Override
    public EmployeeUpdateMailDto refreshEmployeeMail(Integer id, EmployeeUpdateMailReadDto updateMailDto) {
        String oldMail = employeeService.getById(id).getEmail();
        Employee employee = employeeService.updateMailById(id, updateMailDto.newMail());
        String newMail = employee.getEmail();
        return converter.toUpdateMailDto(oldMail, newMail);
    }


    @Override
    public void removeEmployeeById(Integer id) {
        employeeService.removeById(id);
    }

    @Override
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @Override
    public Page<EmployeeDto> findByCountry(String country,
                                            int page,
                                            int size,
                                            List<String> sortList,
                                            Sort.Direction sortOrder) {

        Page<Employee> employeePage = employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
        return employeePage.map(converter::toDto);
    }

    @Override
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @Override
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @Override
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @Override
    public List<EmployeeDto> getByCountry(String country) {
        var employees = employeeService.filterByCountry(country);
        return converter.toDtoList(employees);
    }

    @Override
    public List<EmployeeDto> getEmailsByNull() {
        var employees =  employeeService.filterByEmailIsNull();
        return converter.toDtoList(employees);
    }

    @Override
    public List<EmployeeDto> updateCountryFirstLetterToUpperCase() {
        var employees = employeeService.updateCountryFirstLetterToUpperCase();
        return converter.toDtoList(employees);
    }

    @Override
    public List<EmployeeDto> getEmailIsGmailByCountry(String country) {
        var employees = employeeService.filterByCountryAndGmailEmail(country);
        return converter.toDtoList(employees);
    }

    @Override
    public Integer getCountByGender(String gender) {
            return employeeService.countByGender(gender);
    }

    @Override
    public Set<String> sendEmailsAllUkrainian() {
        return employeeService.sendEmailsAllUkrainian();
    }

    @Override
    public ResponseEntity<?> downloadImage(Integer id) {
        var employee = employeeService.getById(id);
        if (employee == null || employee.getPassport() == null || employee.getPassport().getImage() == null) {
            return ResponseEntity.notFound().build();
        }
        var imageName = employee.getPassport().getImage().getName();
        byte[] imageData = imageService.downloadImage(imageName);

        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageData.length);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public EmployeeDto issuancePassport(PassportReadDto passportDto) {
        var employeeId = passportDto.userId();
        var passportId = passportDto.passportId();
        var employee = employeeService.issuancePassport(employeeId, passportId);
        return converter.toDto(employee);
    }

    @Override
    public EmployeeDto cancelPassport(PassportReadDto passportDto) {
        var employeeId = passportDto.userId();
        var employee = employeeService.cancelPassport(employeeId);
        return converter.toDto(employee);
    }

    @Override
    public List<PassportDto> findByCanceledEmployeeId(Integer id) {
        var passports = employeeService.findByCanceledEmployeeId(id);
        return passportConverter.toDtoListPassports(passports);
    }


}
