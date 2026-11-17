package com.adverto.dejonghe.server.services.employee;

import com.adverto.dejonghe.common.entities.employee.DayPart;
import com.adverto.dejonghe.common.entities.employee.EmployeeAbsence;
import com.adverto.dejonghe.common.repos.EmployeeAbsenceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeAbsenceService {

    private final EmployeeAbsenceRepo repo;

    public EmployeeAbsenceService(EmployeeAbsenceRepo repo) {
        this.repo = repo;
    }

    public List<EmployeeAbsence> findInRange(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to)) {
            return List.of();
        }
        return repo.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(to, from);
    }

    public Optional<EmployeeAbsence> findById(String id) {
        return id == null ? Optional.empty() : repo.findById(id);
    }

    public EmployeeAbsence save(EmployeeAbsence absence) {
        validate(absence);

        List<EmployeeAbsence> existing = repo
                .findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        absence.getEmployeeId(), absence.getEndDate(), absence.getStartDate());

        for (EmployeeAbsence other : existing) {
            if (other.getId() != null && other.getId().equals(absence.getId())) {
                continue;
            }
            if (overlaps(absence, other)) {
                throw new IllegalArgumentException(
                        "Deze medewerker heeft al een afwezigheid in (een deel van) deze periode.");
            }
        }
        return repo.save(absence);
    }

    public void delete(String id) {
        if (id != null && !id.isBlank()) {
            repo.deleteById(id);
        }
    }

    private static void validate(EmployeeAbsence absence) {
        if (absence == null || absence.getEmployeeId() == null
                || absence.getEmployeeId().isBlank()) {
            throw new IllegalArgumentException("Selecteer een personeelslid.");
        }
        if (absence.getType() == null || absence.getStartDate() == null
                || absence.getEndDate() == null || absence.getStartPart() == null
                || absence.getEndPart() == null) {
            throw new IllegalArgumentException("Vul type, datums en dagdelen volledig in.");
        }
        if (position(absence.getStartDate(), absence.getStartPart())
                > position(absence.getEndDate(), absence.getEndPart())) {
            throw new IllegalArgumentException("De einddatum of het einddagdeel ligt voor het begin.");
        }
    }

    private static boolean overlaps(EmployeeAbsence a, EmployeeAbsence b) {
        return position(a.getStartDate(), a.getStartPart())
                <= position(b.getEndDate(), b.getEndPart())
                && position(b.getStartDate(), b.getStartPart())
                <= position(a.getEndDate(), a.getEndPart());
    }

    private static long position(LocalDate date, DayPart part) {
        return date.toEpochDay() * 2L + (part == DayPart.AFTERNOON ? 1 : 0);
    }
}
