package com.automationcompany.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 50, unique = true)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ProjectStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProjectPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ProjectServiceType serviceType;

    @Column(length = 150)
    private String location;

    @ElementCollection
    @CollectionTable(
            name = "project_employees",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "employee_id")
    @Builder.Default
    private Set<Long> employeeIds = new HashSet<>();

    @Column(name = "project_manager_id")
    private Long projectManagerId;

    @ElementCollection(targetClass = ProjectTechnology.class)
    @CollectionTable(
            name = "project_technologies",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "technology")
    @Builder.Default
    private Set<ProjectTechnology> technologies = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String createdBy;

    @Column(length = 100)
    private String updatedBy;
}