package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import com.example.demowithtests.dto.EmployeeUpdateMailReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.util.exception.EmployeePaginationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeConverter converter;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

      //  var employee = converter.getModelMapper().map(requestForSave, Employee.class);
        var employee = converter.toEmployee(requestForSave);
        var dto = converter.toDto(employeeService.create(employee));
        log.debug("saveEmployee() - stop: dto = {}", dto);
        return dto;
    }

    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmployee1(@RequestBody EmployeeDto employeeDto) {
        final var employee = EmployeeConverter.INSTANCE.toEmployee(employeeDto);
        employeeService.create(employee);
    }


    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAllUsers() {
        var employees = employeeService.getAll();
        return converter.toDtoList(employees);
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeDto> getPage(@RequestParam(defaultValue = "2") int page, @RequestParam(defaultValue = "-100") int size) {
        if (page < 0 || size <= 0) {
            throw new EmployeePaginationException("Parameters incorect. Check page and size value", page, size);
        }

        Pageable paging = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getAllWithPagination(paging);
        return employeePage.map(converter::toDto);
    }


    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        var dto = converter.toReadDto(employee);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        Employee employee = converter.toEmployee(employeeDto);
        Employee updatedEmployee = employeeService.updateById(id, employee);
        return converter.toDto(updatedEmployee);
    }

    //Обновление mail юзера
    @PatchMapping("/users/up_m/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeUpdateMailDto refreshEmployeeMail(@PathVariable("id") Integer id, @RequestBody EmployeeUpdateMailReadDto updateMailDto) {
        String oldMail = employeeService.getById(id).getEmail();
        Employee employee = employeeService.updateMailById(id, updateMailDto.newMail());
        String newMail = employee.getEmail();
        return converter.toUpdateMailDto(oldMail, newMail);
    }


    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeDto> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        Page<Employee> employeePage = employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
        return employeePage.map(converter::toDto);
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getByCountry(@RequestParam(required = true) String country) {
        var employees = employeeService.filterByCountry(country);
        return converter.toDtoList(employees);
    }

    @GetMapping("/users/e_null")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmailsByNull() {
        var employees =  employeeService.filterByEmailIsNull();
        return converter.toDtoList(employees);
    }

    @PatchMapping("/users/c_up_upper")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> updateCountryFirstLetterToUpperCase() {
        var employees = employeeService.updateCountryFirstLetterToUpperCase();
        return converter.toDtoList(employees);
    }

    @GetMapping("/users/f_c_gmail")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmailIsGmailByCountry(@RequestParam(required = true) String country) {
        var employees = employeeService.filterByCountryAndGmailEmail(country);
        return converter.toDtoList(employees);
    }

    @GetMapping("/users/g_count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCountByGender(@RequestParam("gender") String gender) {
            return employeeService.countByGender(gender);
    }

    @PatchMapping("/users/ukrainians")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailsAllUkrainian() {
        return employeeService.sendEmailsAllUkrainian();
    }
}
