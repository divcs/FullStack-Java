package com.example.restapi.controller;

import com.example.restapi.model.Employee.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>(
        List.of(
            new Employee(1, "Alice", "HR"),
            new Employee(2, "Bob", "IT"),
            new Employee(3, "Charlie", "Finance")
        )
    );

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employees.stream()
                        .filter(e -> e.getId() == id)
                        .findFirst()
                        .orElse(null);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }
}
