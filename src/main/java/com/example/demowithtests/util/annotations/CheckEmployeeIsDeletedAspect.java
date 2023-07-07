package com.example.demowithtests.util.annotations;


import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.exception.EmployeeNotFoundException;
import com.example.demowithtests.util.exception.EmployeeWasDeletedException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class CheckEmployeeIsDeletedAspect {

    private final EmployeeRepository employeeRepository;

    @Pointcut("@annotation(com.example.demowithtests.util.annotations.service.CheckEmployeeIsDeleted)")
    public void checkEmployeeIsDeletedMethod() {
    }

    @Before("checkEmployeeIsDeletedMethod() && args(id)")
    public void beforeMethod(Integer id) {
        System.out.println("ANNOTATION RUNNING");
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employee.isDeleted()) {
            throw new EmployeeWasDeletedException();
        }
    }
}
