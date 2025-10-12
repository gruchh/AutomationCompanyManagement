package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.dto.EmployeeCreateDTO;
import com.automationcompany.employee.model.dto.EmployeeReadDTO;
import com.automationcompany.employee.model.dto.EmployeeUpdateDTO;
import com.automationcompany.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeReadDTO> create(@Valid @RequestBody EmployeeCreateDTO dto) {
        EmployeeReadDTO createdEmployee = employeeService.create(dto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeReadDTO>> getAll() {
        List<EmployeeReadDTO> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeReadDTO> getById(@PathVariable Long id) {
        EmployeeReadDTO employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeReadDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        EmployeeReadDTO updatedEmployee = employeeService.update(id, dto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}