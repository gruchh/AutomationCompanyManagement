package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.dto.EmployeeCreateDto;
import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.employee.model.dto.EmployeeUpdateDto;
import com.automationcompany.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "API for managing employee data")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create new employee", description = "Creates a new employee record")
    public ResponseEntity<EmployeeReadDto> create(@Valid @RequestBody EmployeeCreateDto dto) {
        EmployeeReadDto createdEmployee = employeeService.create(dto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieves list of all employees")
    public ResponseEntity<List<EmployeeReadDto>> getAll() {
        List<EmployeeReadDto> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieves specific employee by ID")
    public ResponseEntity<EmployeeReadDto> getById(
            @Parameter(description = "Employee ID", required = true)
            @PathVariable Long id) {
        EmployeeReadDto employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update employee", description = "Partially updates employee data")
    public ResponseEntity<EmployeeReadDto> update(
            @Parameter(description = "Employee ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDto dto) {
        EmployeeReadDto updatedEmployee = employeeService.update(id, dto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes employee by ID")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Employee ID", required = true)
            @PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user", description = "Returns details of the currently authenticated user based on the Bearer token")
    public ResponseEntity<Object> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "roles", authentication.getAuthorities()
                )
        );
    }
}