package com.example.demowithtests.service.em;

import com.example.demowithtests.domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceEMBean implements EmployeeServiceEM {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param employee
     * @return
     */
    @Override
    @Transactional //jakarta
    public Employee createWithJpa(Employee employee) {
        return entityManager.merge(employee);
        /*entityManager.persist(employee);
        entityManager.flush();
        return entityManager.find(Employee.class, employee);*/
    }

    /**
     * @return
     */
    @Override
    @Transactional //jakarta
    public Set<String> findAllCountriesWithJpa() {
        return entityManager.createQuery("select distinct country from Employee", String.class).getResultStream().collect(Collectors.toSet());
    }

    /**
     * @param id
     * @param employee
     * @return
     */
    @Override
    @Transactional //jakarta
    public Employee updateByIdWithJpa(Integer id, Employee employee) {
        Employee refreshEmployee = Optional.ofNullable(entityManager.find(Employee.class, id))
                .orElseThrow(() -> new RuntimeException("id = " + employee.getId()));
        return entityManager.merge(refreshEmployee);
    }

    /**
     * @param id
     */
    @Override
    @Transactional //jakarta
    public void deleteByIdWithJpa(Integer id) {
        Optional<Employee> employee = Optional.ofNullable(entityManager.find(Employee.class, id));
        entityManager.remove(employee);
    }
}
