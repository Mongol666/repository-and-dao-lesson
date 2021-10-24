package com.dbit.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends AbstractEntity {
    private String name;
    private City city;
    private List<Employee> employees = new ArrayList<>();

    public Department withId(Integer id) {
        setId(id);
        return this;
    }

    public Department withName(String name) {
        setName(name);
        return this;
    }

    public Department withCity(City city) {
        setCity(city);
        return this;
    }
}
