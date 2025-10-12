package com.automationcompany.employee.service;

import com.automationcompany.employee.exception.EmployeeAlreadyExistsException;
import com.automationcompany.employee.exception.EmployeeNotFoundException;
import com.automationcompany.employee.mapper.EmployeeMapper;
import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.dto.EmployeeCreateDTO;
import com.automationcompany.employee.model.dto.EmployeeReadDTO;
import com.automationcompany.employee.model.dto.EmployeeUpdateDTO;
import com.automationcompany.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeReadDTO create(EmployeeCreateDTO dto) {
        log.info("Creating new employee with email: {}", dto.getEmail());

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with email " + dto.getEmail() + " already exists");
        }

        if (employeeRepository.existsByPesel(dto.getPesel())) {
            throw new EmployeeAlreadyExistsException("Employee with PESEL " + dto.getPesel() + " already exists");
        }

        Employee employee = employeeMapper.toEntity(dto);
        Employee saved = employeeRepository.save(employee);

        log.info("Employee created successfully with id: {}", saved.getId());
        return employeeMapper.toReadDTO(saved);
    }

    @Transactional
    public EmployeeReadDTO update(Long id, EmployeeUpdateDTO dto) {
        log.info("Updating employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeMapper.updateEmployeeFromDto(dto, employee);
        Employee updated = employeeRepository.save(employee);

        log.info("Employee updated successfully with id: {}", id);
        return employeeMapper.toReadDTO(updated);
    }

    @Transactional(readOnly = true)
    public List<EmployeeReadDTO> findAll() {
        log.info("Fetching all employees");

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeReadDTO findById(Long id) {
        log.info("Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeMapper.toReadDTO(employee);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting employee with id: {}", id);

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully with id: {}", id);
    }
}