package com.dbit.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractEntity {
    private String name;
    private Title title;
    private int salary;
    private List<Department> departments = new ArrayList<>();

    public Employee withId(Integer id) {
        setId(id);
        return this;
    }

    public Employee withName(String name) {
        setName(name);
        return this;
    }

    public Employee withSalary(Integer salary) {
        setSalary(salary);
        return this;
    }

    public Employee withTitle(Title title) {
        setTitle(title);
        return this;
    }

    public Employee addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
        return this;
    }
}
