package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.employee.EmployeeAbsence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeAbsenceRepo extends MongoRepository<EmployeeAbsence, String> {

    // A record overlaps [from, to] when record.start <= to and record.end >= from.
    List<EmployeeAbsence> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate to, LocalDate from);

    List<EmployeeAbsence> findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String employeeId, LocalDate to, LocalDate from);
}
