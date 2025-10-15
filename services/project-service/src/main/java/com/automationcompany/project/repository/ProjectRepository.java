package com.automationcompany.project.repository;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
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

    boolean existsByCode(String code);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByProjectManagerId(Long projectManagerId);

    @Query("SELECT DISTINCT p FROM Project p WHERE :employeeId MEMBER OF p.employeeIds")
    List<Project> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT p FROM Project p WHERE " +
            "(p.startDate <= :endDate AND (p.endDate IS NULL OR p.endDate >= :startDate))")
    List<Project> findActiveProjectsBetween(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE p.status IN :statuses")
    List<Project> findByStatusIn(@Param("statuses") List<ProjectStatus> statuses);

    @Query("SELECT p FROM Project p WHERE p.startDate BETWEEN :startDate AND :endDate")
    List<Project> findByStartDateBetween(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE p.endDate BETWEEN :startDate AND :endDate")
    List<Project> findByEndDateBetween(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Project p WHERE p.status = 'ACTIVE' OR " +
            "(p.status = 'IN_PROGRESS' AND (p.endDate IS NULL OR p.endDate > CURRENT_DATE))")
    List<Project> findActiveProjects();

    List<Project> findByProjectManagerIdIsNull();

    @Query("SELECT p FROM Project p WHERE p.employeeIds IS NOT EMPTY AND SIZE(p.employeeIds) > 0")
    List<Project> findByEmployeeIdsNotEmpty();

    List<Project> findByServiceType(ProjectServiceType serviceType);

    @Query("SELECT p FROM Project p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Project> findByNameOrCodeContainingIgnoreCase(@Param("search") String search);

    @Query("SELECT p FROM Project p ORDER BY p.startDate DESC")
    List<Project> findAllOrderByStartDateDesc();

    List<Project> findByPriority(ProjectPriority priority);
}