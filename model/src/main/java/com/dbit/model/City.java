package com.dbit.model;

import lombok.*;

import java.util.List;
import java.util.ArrayList;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City extends AbstractEntity {
    private String name;
    private List<Department> departments = new ArrayList<>();

    public City withId(Integer id) {
        setId(id);
        return this;
    }

    public City withName(String name) {
        setName(name);
        return this;
    }

    public City addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
        return this;
    }
}
