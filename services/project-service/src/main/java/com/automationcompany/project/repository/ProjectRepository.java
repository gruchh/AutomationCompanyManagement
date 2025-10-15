package com.automationcompany.project.repository;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByCode(String code);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByProjectManagerId(Long projectManagerId);

    @Query("SELECT p FROM Project p WHERE :employeeId MEMBER OF p.employeeIds")
    List<Project> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT p FROM Project p WHERE p.startDate >= :startDate AND p.startDate <= :endDate")
    List<Project> findByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE p.endDate >= :startDate AND p.endDate <= :endDate")
    List<Project> findByEndDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE " +
            "(p.startDate <= :endDate AND (p.endDate IS NULL OR p.endDate >= :startDate))")
    List<Project> findActiveProjectsBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE p.status IN :statuses")
    List<Project> findByStatusIn(@Param("statuses") List<ProjectStatus> statuses);

    boolean existsByCode(String code);
}