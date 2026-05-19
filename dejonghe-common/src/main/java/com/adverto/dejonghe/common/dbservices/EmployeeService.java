package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    public Optional<List<Employee>> getEmployeeByFirstName(String firstName) {
        return Optional.of(employeeRepo.findByFirstNameContainingIgnoreCase(firstName));
    }

    public Optional<List<Employee>> getAll() {
        List<Employee> employees = employeeRepo.findAll();
        if (!employees.isEmpty()) {
            employees.sort(Comparator.comparing(Employee::getTechnician).reversed());
            return Optional.of(employees);
        }
        else{
            return Optional.empty();
        }
    }

    public void delete(Employee employee) {
        employeeRepo.delete(employee);
    }

    public void save(Employee employee) {
        if (employee.getAbbreviation() != null) {
            employeeRepo.save(employee);
        }
    }
}
