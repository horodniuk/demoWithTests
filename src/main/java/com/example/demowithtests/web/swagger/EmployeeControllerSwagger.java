package com.example.demowithtests.web.swagger;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import com.example.demowithtests.dto.EmployeeUpdateMailReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Tag(name = "Employee", description = "Employee API")
    public interface EmployeeControllerSwagger {

    @Operation(summary = "This is endpoint to add a new employee.",
                description = "Create request to add a new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave);




    @Operation(summary = "This is endpoint to add a new employee.",
            description = "Create request to add a new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to the database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    void saveEmployee1(@RequestBody @Valid EmployeeDto employeeDto);


    @Operation(summary = "This is endpoint to get all employees.",
            description = "Create request to get all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<EmployeeDto> getAllUsers();


    @Operation(summary = "This is endpoint to get page of all employees.",
               description = "Create request to get page of employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),})
    Page<EmployeeDto> getPage(
            @Parameter(description = "Page number", example = "1", required = true)
            int page,
            @Parameter(description = "Page size", example = "2", required = true)
            int size);


    @Operation(summary = "This is endpoint returned a employee by his id.",
               description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto getEmployeeById(
            @Parameter(description = "Employee ID", example = "1", required = true)
            Integer id);


    @Operation(summary = "This is endpoint to refresh an employee.",
            description = "Create request to refresh an employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    EmployeeDto refreshEmployee(Integer id, @RequestBody EmployeeDto employeeDto);


    @Operation(summary = "This is endpoint to refresh an employee's mail.",
            description = "Create request to refresh an employee's mail.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    EmployeeUpdateMailDto refreshEmployeeMail(Integer id, EmployeeUpdateMailReadDto updateMailDto);


    @Operation(summary = "This is endpoint to remove an employee by their ID.",
            description = "Create request to remove an employee by their ID.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    void removeEmployeeById(Integer id);


    @Operation(summary = "This is endpoint to remove all employees.",
            description = "Create request to remove all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")})
    void removeAllUsers();


    @Operation(summary = "This is endpoint to find employees by country.",
            description = "Create request to find employees by country.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    Page<EmployeeDto> findByCountry( String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);


    @Operation(summary = "This is endpoint to get all user countries.",
            description = "Create request to get all user countries.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<String> getAllUsersC();


    @Operation(summary = "This is endpoint to get all user countries sorted.",
            description = "Create request to get all user countries sorted.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<String> getAllUsersSort();


    @Operation(summary = "This is endpoint to get all user countries sorted.",
            description = "Create request to get all user countries sorted.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    Optional<String> getAllUsersSo();


    @Operation(summary = "This is endpoint to get employees by country.",
            description = "Create request to get employees by country.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<EmployeeDto> getByCountry(@RequestParam(required = true) String country);


    @Operation(summary = "This is endpoint to get emails where the email value is null.",
            description = "Create request to get emails where the email value is null.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<EmployeeDto> getEmailsByNull();


    @Operation(summary = "This is endpoint to update the first letter of the country to uppercase for all employees.",
            description = "Create request to update the first letter of the country to uppercase for all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<EmployeeDto> updateCountryFirstLetterToUpperCase();


    @Operation(summary = "This is endpoint to get employees with Gmail email addresses in a specific country.",
            description = "Create request to get employees with Gmail email addresses in a specific country.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    List<EmployeeDto> getEmailIsGmailByCountry(String country);


    @Operation(summary = "This is endpoint to get the count of employees by gender.",
            description = "Create request to get the count of employees by gender.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    Integer getCountByGender(String gender);


    @Operation(summary = "This is endpoint to send emails to all Ukrainian employees.",
            description = "Create request to send emails to all Ukrainian employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    Set<String> sendEmailsAllUkrainian();
}
