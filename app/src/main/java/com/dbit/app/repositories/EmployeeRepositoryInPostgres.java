package com.dbit.app.repositories;

import com.dbit.app.exceptions.DatabaseException;
import com.dbit.model.City;
import com.dbit.model.Department;
import com.dbit.model.Employee;
import com.dbit.model.Title;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class EmployeeRepositoryInPostgres implements EmployeeRepository {

    //language=SQL
    private static final String SELECT_EMPLOYEES_ALL_FIELDS =
            "select " +
                    "e.id e_id, e.name employee_name, salary," +
                    "d.id d_id, d.name department_name," +
                    "c.id c_id, c.name city_name, " +
                    "t.id t_id, t.name title_name " +
                    "from employee e " +
                    "join title t " +
                    "on t.id = e.title_id " +
                    "join department_employee de " +
                    "on e.id = de.employee_id " +
                    "join department d " +
                    "on d.id = de.department_id " +
                    "join city c " +
                    "on d.city_id = c.id";

    public static final String E_ID = "e_id";
    public static final String D_ID = "d_id";
    public static final String C_ID = "c_id";
    public static final String T_ID = "t_id";

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
        Map<Integer, Employee> employeeMap = new HashMap<>();
       try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEES_ALL_FIELDS);
            ResultSet set = statement.executeQuery())
       {
           Map<Integer, Department> departmentMap = new HashMap<>();
           Map<Integer, City> cityMap = new HashMap<>();
           Map<Integer, Title> titleMap = new HashMap<>();
            while (set.next()) {
                int e_id = set.getInt(E_ID);
                int d_id = set.getInt(D_ID);
                int c_id = set.getInt(C_ID);
                int t_id = set.getInt(T_ID);

                employeeMap.putIfAbsent(e_id, new Employee()
                        .withId(e_id)
                        .withName(set.getString("employee_name"))
                        .withTitle(putIfAbsentAndReturn(titleMap, t_id,
                                new Title()
                                        .withId(t_id)
                                        .withName(set.getString("title_name"))))
                        .withSalary(set.getInt("salary"))
                        .addDepartment(putIfAbsentAndReturn(departmentMap, d_id,
                                new Department()
                                        .withId(d_id)
                                        .withName(set.getString("department_name"))
                                        .withCity(putIfAbsentAndReturn(cityMap, c_id,
                                                new City()
                                                        .withId(c_id)
                                                        .withName(set.getString("city_name")))))));

                employeeMap.computeIfPresent(e_id, (id, employee) -> employee.addDepartment(departmentMap.get(d_id)));
            }
       } catch (SQLException e) {
           log.error(e.getMessage(), e);
           throw new DatabaseException(e);
       }
       return new ArrayList<>(employeeMap.values());
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

    private static <K, V> V putIfAbsentAndReturn(Map<K, V> map, K key, V value) {
        if (key == null) {
            return null;
        }
        map.putIfAbsent(key, value);
        return map.get(key);
    }
}
