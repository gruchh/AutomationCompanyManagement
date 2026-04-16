package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO used for filtering and searching projects")
public class ProjectFilterDto {

    @Schema(description = "Filter by project statuses", example = "[\"ACTIVE\", \"COMPLETED\"]")
    private List<ProjectStatus> statuses;

    @Schema(description = "Filter by service type", example = "[\"OUTSOURCING\", \"MAINTENANCE\"]")
    private List<ProjectServiceType> serviceTypes;

    @Schema(description = "Filter by project priority", example = "[\"HIGH\", \"MEDIUM\"]")
    private List<ProjectPriority> priorities;

    @Schema(description = "Filter by technologies used in project", example = "[\"JAVA\", \"ANGULAR\"]")
    private List<ProjectTechnology> technologies;

    @Schema(description = "Filter by project manager ID", example = "12")
    private Long managerId;

    @Schema(description = "Filter by assigned employee ID", example = "34")
    private Long employeeId;

    @Schema(description = "Filter by location ID", example = "5")
    private Long locationId;

    @Schema(description = "Filter by partial location name (case-insensitive)", example = "Warsaw")
    private String locationName;

    @Schema(description = "Filter by country", example = "Poland")
    private String country;

    @Schema(description = "Filter by city", example = "Katowice")
    private String city;

    @Schema(description = "Start date from (inclusive)", example = "2025-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateFrom;

    @Schema(description = "Start date to (inclusive)", example = "2025-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateTo;

    @Schema(description = "End date from (inclusive)", example = "2025-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateFrom;

    @Schema(description = "End date to (inclusive)", example = "2025-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTo;

    @Schema(description = "Global search (project name + description)", example = "crm system")
    private String searchQuery;

    @Schema(description = "Minimum team size", example = "3")
    private Integer minTeamSize;

    @Schema(description = "Maximum team size", example = "20")
    private Integer maxTeamSize;

    @Schema(description = "Field used for sorting results",
            example = "startDate",
            allowableValues = {"startDate", "endDate", "name", "createdAt"})
    @Builder.Default
    private String sortBy = "startDate";

    @Schema(description = "Sort direction",
            example = "desc",
            allowableValues = {"asc", "desc"})
    @Builder.Default
    private String sortDirection = "desc";
}