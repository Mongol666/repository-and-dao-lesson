package com.dbit.app.repositories;

import com.dbit.app.exceptions.DatabaseException;
import com.dbit.model.Employee;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class EmployeeRepositoryInPostgres implements EmployeeRepository {

    private final DataSource dataSource;
    private static volatile EmployeeRepositoryInPostgres instance;

    private EmployeeRepositoryInPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static EmployeeRepositoryInPostgres getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (EmployeeRepositoryInPostgres.class) {
                if (instance == null) {
                    instance = new EmployeeRepositoryInPostgres(dataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
       try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee");
            ResultSet set = statement.executeQuery())
       {
            while (set.next()) {
                employees.add(new Employee()
                        .withId(set.getInt("id"))
                        .withName(set.getString("name"))
                        .withSalary(set.getInt("salary")));
            }
       } catch (SQLException e) {
           log.error(e.getMessage(), e);
           throw new DatabaseException(e);
       }
       return employees;
    }

    @Override
    public Optional<Employee> findByID(int id) {
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        return null;
    }

    @Override
    public Optional<Employee> remove(Employee employee) {
        return Optional.empty();
    }
}
