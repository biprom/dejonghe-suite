package com.adverto.dejonghe.common.entities.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/** Dated exception. The recurring weeklySchedule on Employee stays unchanged. */
@Document(collection = "employeeAbsences")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeAbsence {

    @Id
    private String id;
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private DayPart startPart = DayPart.MORNING;
    private DayPart endPart = DayPart.AFTERNOON;
    private AbsenceType type = AbsenceType.VACATION;
    private String comment;
}
