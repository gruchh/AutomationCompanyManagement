package com.automationcompany.employee.repository;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.EmployeeKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeKeysRepository extends JpaRepository<EmployeeKeys, Long> {
    Optional<EmployeeKeys> findByEmployee(Employee employee);
    boolean existsByEmployee(Employee employee);
}