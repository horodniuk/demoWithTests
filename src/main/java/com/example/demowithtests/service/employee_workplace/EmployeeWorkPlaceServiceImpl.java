package com.example.demowithtests.service.employee_workplace;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeWorkPlace;
import com.example.demowithtests.domain.EmployeeWorkPlacePK;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.EmployeeWorkPlaceRepository;
import com.example.demowithtests.repository.WorkPlaceRepository;
import com.example.demowithtests.util.exception.employee_workplace.WorkPlaceLimitException;
import com.example.demowithtests.util.exception.employee_workplace.WorkPlaceNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeWorkPlaceServiceImpl implements EmployeeWorkPlaceService {
    private final EmployeeWorkPlaceRepository employeeWorkPlaceRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkPlaceRepository workPlaceRepository;
    private static final int WORK_PLACE_LIMIT_BY_ONE_EMPLOYEE = 3;

    @Override
    public EmployeeWorkPlace save(EmployeeWorkPlace employeeWorkPlace) {
        return employeeWorkPlaceRepository.save(employeeWorkPlace);
    }

    @Override
    public EmployeeWorkPlace deactivateEmployeeWorkPlace(Integer employeeId, Integer workPlaceId) {
        EmployeeWorkPlace employeeWorkPlace = getActiveEmployeeWorkPlace(employeeId, workPlaceId);
        employeeWorkPlace.setIsActive(Boolean.FALSE);
        return employeeWorkPlaceRepository.save(employeeWorkPlace);
    }

    private EmployeeWorkPlace getActiveEmployeeWorkPlace(Integer employeeId, Integer workPlaceId) {
        return employeeWorkPlaceRepository
                .findByEmployeeWorkPlacePK_EmployeeIdAndEmployeeWorkPlacePK_WorkPlaceIdAndIsActive(employeeId, workPlaceId, Boolean.TRUE)
                .orElseThrow(() -> new EntityNotFoundException("Active EmployeeWorkPlace not found"));
    }

    @Override
    public List<EmployeeWorkPlace> findByEmployeeId(Integer employeeId) {
        return employeeWorkPlaceRepository.findByEmployeeWorkPlacePK_EmployeeIdAndIsActive(employeeId, Boolean.TRUE);
    }

    @Override
    public boolean checkLimitWorkPlace(Integer employeeId) {
        List<EmployeeWorkPlace> workPlaces = findByEmployeeId(employeeId);
        return workPlaces.size() < WORK_PLACE_LIMIT_BY_ONE_EMPLOYEE;
    }

    private void validateWorkPlaceLimit(Integer employeeId) {
        if (!checkLimitWorkPlace(employeeId)) {
            throw new WorkPlaceLimitException("Employee has maximum number of work places - " + WORK_PLACE_LIMIT_BY_ONE_EMPLOYEE);
        }
    }

    @Override
    public EmployeeWorkPlace addWorkPlaceToEmployee(Integer employeeId, Integer workPlaceId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        validateWorkPlaceLimit(employeeId);
        Employee employee = getEmployeeById(employeeId);
        WorkPlace workPlace = getWorkPlaceById(workPlaceId);
        validateWorkPlaceAviable(workPlaceId, reservationStartTime, reservationEndTime);
        EmployeeWorkPlace employeeWorkPlace = createEmployeeWorkPlace(employee, workPlace, reservationStartTime, reservationEndTime);
        return save(employeeWorkPlace);
    }

    private void validateWorkPlaceAviable(Integer workPlaceId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        if (!isWorkPlaceAvailable(workPlaceId, reservationStartTime, reservationEndTime)) {
            throw new WorkPlaceNotAvailableException("Workplace id = " + workPlaceId + " is not available for " + reservationStartTime + " to " + reservationEndTime);
        }
    }

    public boolean isWorkPlaceAvailable(Integer workPlaceId, LocalDateTime startTime, LocalDateTime endTime) {
        List<EmployeeWorkPlace> occupiedWorkPlaces = employeeWorkPlaceRepository
                .findByWorkPlaceIdAndReservationEndTimeAfterAndReservationStartTimeBeforeAndIsActive(workPlaceId, startTime, endTime, Boolean.TRUE);
        return occupiedWorkPlaces.isEmpty();
    }

    private Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found"));
    }

    private WorkPlace getWorkPlaceById(Integer workPlaceId) {
        return workPlaceRepository.findById(workPlaceId)
                .orElseThrow(() -> new EntityNotFoundException("Workplace with ID " + workPlaceId + " not found"));
    }

    private EmployeeWorkPlace createEmployeeWorkPlace(Employee employee, WorkPlace workPlace, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        EmployeeWorkPlacePK employeeWorkPlacePK = new EmployeeWorkPlacePK(employee.getId(), workPlace.getId());
        return EmployeeWorkPlace.builder()
                .employeeWorkPlacePK(employeeWorkPlacePK)
                .employee(employee)
                .workPlace(workPlace)
                .reservationStartTime(reservationStartTime)
                .reservationEndTime(reservationEndTime)
                .isActive(Boolean.TRUE)
                .build();
    }


}
