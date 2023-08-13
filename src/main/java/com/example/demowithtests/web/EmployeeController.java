package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import com.example.demowithtests.dto.EmployeeUpdateMailReadDto;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.dto.passport.PassportReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EmployeeController {

    @PostMapping("/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave);

    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    void saveEmployee1(@RequestBody EmployeeDto employeeDto);

    @GetMapping("/users")
    List<EmployeeDto> getAllUsers();

    @GetMapping("/users/p")
    Page<EmployeeDto> getPage(@RequestParam(defaultValue = "2") int page,
                              @RequestParam(defaultValue = "-3") int size);


    @GetMapping("/users/{id}")
    EmployeeReadDto getEmployeeById(@PathVariable Integer id);

    @PutMapping("/users/{id}")
    EmployeeDto refreshEmployee(@PathVariable("id") Integer id,
                                @RequestBody EmployeeDto employeeDto);

    @PatchMapping("/users/up_m/{id}")
    EmployeeUpdateMailDto refreshEmployeeMail(@PathVariable("id") Integer id,
                                              @RequestBody EmployeeUpdateMailReadDto updateMailDto);


    @PatchMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void removeEmployeeById(@PathVariable Integer id);

    @DeleteMapping("/users")
    void removeAllUsers();

    @GetMapping("/users/country")
    Page<EmployeeDto> findByCountry(@RequestParam(required = false) String country,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "3") int size,
                                    @RequestParam(defaultValue = "") List<String> sortList,
                                    @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);


    @GetMapping("/users/c")
    List<String> getAllUsersC();

    @GetMapping("/users/s")
    List<String> getAllUsersSort();

    @GetMapping("/users/emails")
    Optional<String> getAllUsersSo();

    @GetMapping("/users/countryBy")
    List<EmployeeDto> getByCountry(@RequestParam(required = true) String country);

    @GetMapping("/users/e_null")
    List<EmployeeDto> getEmailsByNull();

    @PatchMapping("/users/c_up_upper")
    List<EmployeeDto> updateCountryFirstLetterToUpperCase();

    @GetMapping("/users/f_c_gmail")
    List<EmployeeDto> getEmailIsGmailByCountry(@RequestParam(required = true) String country);

    @GetMapping("/users/g_count")
    Integer getCountByGender(@RequestParam("gender") String gender);

    @PatchMapping("/users/ukrainians")
    Set<String> sendEmailsAllUkrainian();

    @GetMapping("/{id}/image")
    ResponseEntity<?> downloadImage(@PathVariable Integer id);

    @PatchMapping("/users/passport")
    EmployeeDto issuancePassport(@RequestBody PassportReadDto passportReadDto);

    @PatchMapping("/users/passport/cancel")
    EmployeeDto cancelPassport(@RequestBody PassportReadDto passportReadDto);

    @GetMapping("/users/{id}/p_cancel/")
    List<PassportDto> findByCanceledEmployeeId(@PathVariable Integer id);
}
