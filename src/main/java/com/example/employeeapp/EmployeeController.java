package com.example.employeeapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeController() {

        employees.add(new Employee(
                1L,
                "John Smith",
                "john.smith@example.com",
                "IT",
                "Software Engineer",
                "Active"
        ));

        employees.add(new Employee(
                2L,
                "Mary Johnson",
                "mary.johnson@example.com",
                "HR",
                "HR Manager",
                "Active"
        ));

        employees.add(new Employee(
                3L,
                "David Wilson",
                "david.wilson@example.com",
                "Finance",
                "Financial Analyst",
                "Active"
        ));

        employees.add(new Employee(
                4L,
                "Sarah Brown",
                "sarah.brown@example.com",
                "Marketing",
                "Marketing Executive",
                "Active"
        ));

        employees.add(new Employee(
                5L,
                "Michael Davis",
                "michael.davis@example.com",
                "IT",
                "DevOps Engineer",
                "Active"
        ));
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {

        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody Employee employee) {

        long newId = employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0) + 1;

        employee.setId(newId);

        if (employee.getStatus() == null ||
                employee.getStatus().isBlank()) {
            employee.setStatus("Active");
        }

        employees.add(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Long id) {

        boolean removed = employees.removeIf(
                employee -> employee.getId().equals(id)
        );

        if (!removed) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                "Employee deleted successfully"
        );
    }

    @GetMapping("/count")
    public long employeeCount() {
        return employees.size();
    }
}
