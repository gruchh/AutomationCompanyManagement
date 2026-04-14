package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectFilterDto {

    private List<ProjectStatus> statuses;
    private List<ProjectServiceType> serviceTypes;
    private List<ProjectPriority> priorities;
    private List<ProjectTechnology> technologies;

    private Long managerId;
    private Long employeeId;

    private Long locationId;
    private String locationName;
    private String country;
    private String city;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTo;

    private String searchQuery;

    private Integer minTeamSize;
    private Integer maxTeamSize;

    @Builder.Default
    private String sortBy = "startDate";

    @Builder.Default
    private String sortDirection = "desc";
}