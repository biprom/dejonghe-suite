package com.adverto.dejonghe.common.entities.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** Availability for one recurring weekday (not a dated absence). */
@Getter
@Setter
@NoArgsConstructor
public class WorkDayAvailability {
    private DayOfWeek dayOfWeek;
    private boolean morning;
    private boolean afternoon;

    public WorkDayAvailability(DayOfWeek dayOfWeek, boolean morning, boolean afternoon) {
        this.dayOfWeek = dayOfWeek;
        this.morning = morning;
        this.afternoon = afternoon;
    }

    /** Monday-Friday available; Saturday-Sunday unavailable. */
    public static List<WorkDayAvailability> defaultWeek() {
        List<WorkDayAvailability> result = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            boolean workingDay = day.getValue() <= DayOfWeek.FRIDAY.getValue();
            result.add(new WorkDayAvailability(day, workingDay, workingDay));
        }
        return result;
    }

    /**
     * Supports existing MongoDB employees with a missing/null schedule.
     * Returns a new list so the dialog never edits the persisted list before Save.
     */
    public static List<WorkDayAvailability> normalize(List<WorkDayAvailability> source) {
        Map<DayOfWeek, WorkDayAvailability> byDay = new EnumMap<>(DayOfWeek.class);
        if (source != null) {
            for (WorkDayAvailability item : source) {
                if (item != null && item.getDayOfWeek() != null) {
                    byDay.put(item.getDayOfWeek(), item);
                }
            }
        }

        List<WorkDayAvailability> result = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            WorkDayAvailability existing = byDay.get(day);
            if (existing != null) {
                result.add(new WorkDayAvailability(day, existing.isMorning(), existing.isAfternoon()));
            } else {
                boolean workingDay = day.getValue() <= DayOfWeek.FRIDAY.getValue();
                result.add(new WorkDayAvailability(day, workingDay, workingDay));
            }
        }
        return result;
    }
}
