package com.automationcompany.project.repository;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.CountByGroupDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    boolean existsByCode(String code);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByProjectManagerId(Long projectManagerId);

    @Query("SELECT DISTINCT p FROM Project p WHERE :employeeId MEMBER OF p.employeeIds")
    List<Project> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("""
        SELECT p FROM Project p 
        WHERE p.startDate <= :endDate 
        AND (p.endDate IS NULL OR p.endDate >= :startDate)
        """)
    List<Project> findActiveProjectsBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Long countByStatus(ProjectStatus status);

    @Query("""
        SELECT COUNT(p) FROM Project p 
        WHERE p.status NOT IN ('COMPLETED', 'CANCELLED')
        AND p.endDate IS NOT NULL
        AND p.endDate < CURRENT_DATE
        """)
    Long countProjectsPastDeadline();

    @Query("""
        SELECT AVG(CAST((p.endDate - p.startDate) AS double))
        FROM Project p
        WHERE p.status = 'COMPLETED'
        AND p.endDate IS NOT NULL
        """)
    Double getAverageCompletedProjectDuration();

    @Query("""
        SELECT new com.automationcompany.project.model.dto.CountByGroupDto(
            CAST(p.status AS string), COUNT(p)
        )
        FROM Project p 
        GROUP BY p.status
        """)
    List<CountByGroupDto> countProjectsByStatus();

    @Query("""
        SELECT new com.automationcompany.project.model.dto.CountByGroupDto(
            CAST(p.serviceType AS string), COUNT(p)
        )
        FROM Project p 
        GROUP BY p.serviceType
        """)
    List<CountByGroupDto> countProjectsByServiceType();

    @Query("""
        SELECT new com.automationcompany.project.model.dto.CountByGroupDto(
            p.location.name, COUNT(p)
        )
        FROM Project p
        WHERE p.location IS NOT NULL
        GROUP BY p.location.name
        """)
    List<CountByGroupDto> countProjectsByLocation();

    @Query("""
        SELECT new com.automationcompany.project.model.dto.CountByGroupDto(
            CONCAT('Manager ', CAST(p.projectManagerId AS string)), COUNT(p)
        )
        FROM Project p 
        WHERE p.projectManagerId IS NOT NULL 
        AND p.status = 'IN_PROGRESS'
        GROUP BY p.projectManagerId
        """)
    List<CountByGroupDto> countActiveProjectsByManager();

    List<Project> findByEndDateBetweenAndStatusIsNot(
            LocalDate start,
            LocalDate end,
            ProjectStatus projectStatus
    );

    @Query("""
        SELECT DISTINCT p FROM Project p
        LEFT JOIN FETCH p.location
        WHERE p.location IS NOT NULL
        """)
    List<Project> findAllWithLocation();

    @Query("""
        SELECT DISTINCT p FROM Project p
        LEFT JOIN FETCH p.location
        WHERE p.status IN :statuses
        AND p.location IS NOT NULL
        """)
    List<Project> findByStatusInWithLocation(@Param("statuses") List<ProjectStatus> statuses);
}