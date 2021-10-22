package com.dbit.app.repositories;

import com.dbit.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();
    Optional<Employee> findByID(int id);
    Employee save(Employee employee);
    Optional<Employee> remove(Employee employee);
}
