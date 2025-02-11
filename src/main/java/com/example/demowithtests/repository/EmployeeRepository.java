package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = "select * from users join addresses on users.id = addresses.employee_id " +
            "where users.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    Employee findByName(String name);


    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    Page<Employee> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Employee> findByIdAndIsDeletedFalse(Integer id);

    List<Employee> findAllByIsDeletedFalse();

    List<Employee> findByIsDeletedFalseAndEmailIsNull();

    @Query(value = "SELECT e FROM Employee e WHERE" +
           " e.isDeleted = false AND" +
           " LOWER(SUBSTRING(e.country, 1, 1)) = SUBSTRING(e.country, 1, 1)")
    List<Employee> findByCountryFirstLetterLowerCase();


    @Query("SELECT e FROM Employee e WHERE e.country = :country AND e.email LIKE '%@gmail.com'")
    List<Employee> findByCountryAndEmailIsGmail(String country);


    @Query("SELECT COUNT(e) FROM Employee e WHERE e.gender = :gender")
    Integer countByGender(Gender gender);


    @Query(value = "SELECT * FROM users WHERE country = 'Ukraine'", nativeQuery = true)
    Optional<List<Employee>> findAllUkrainian();
}
