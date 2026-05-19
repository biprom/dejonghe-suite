package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.employee.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepo extends MongoRepository<Employee, String> {
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
}
