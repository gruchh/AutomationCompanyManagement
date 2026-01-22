package com.automationcompany.project.controller;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import com.automationcompany.project.model.dto.*;
import com.automationcompany.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "API for managing projects, teams and assignments")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all projects", description = "Retrieves a complete list of all projects")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project details", description = "Retrieves detailed information about a specific project")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/{id}/with-employees")
    @Operation(summary = "Get project with team", description = "Retrieves project details including assigned employees and manager")
    public ResponseEntity<ProjectWithEmployeesDto> getProjectWithEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectWithEmployees(id));
    }

    @PostMapping
    @Operation(summary = "Create project", description = "Creates a new project with initial configuration and team")
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody @Schema(description = "Project creation request") ProjectCreateDto createDto) {
        ProjectDto createdProject = projectService.createProject(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project", description = "Updates project details, status, dates or team assignments")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateDto updateDto) {
        return ResponseEntity.ok(projectService.updateProject(id, updateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project", description = "Permanently removes a project and all its assignments")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/employees")
    @Operation(summary = "Assign team members", description = "Adds employees to a project team")
    public ResponseEntity<ProjectDto> assignEmployees(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "List of employee IDs to assign", example = "[2, 3, 5]")
            @RequestBody List<Long> employeeIds) {
        return ResponseEntity.ok(projectService.assignEmployees(id, employeeIds));
    }

    @DeleteMapping("/{id}/employees")
    @Operation(summary = "Remove team members", description = "Removes employees from a project team")
    public ResponseEntity<ProjectDto> removeEmployees(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "List of employee IDs to remove", example = "[2, 3]")
            @RequestBody Set<Long> employeeIds) {
        return ResponseEntity.ok(projectService.removeEmployees(id, employeeIds));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filter projects by status", description = "Retrieves projects with a specific status")
    public ResponseEntity<List<ProjectDto>> getProjectsByStatus(
            @Parameter(description = "Project status filter", required = true, example = "ACTIVE")
            @PathVariable ProjectStatus status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Employee projects", description = "Finds all projects assigned to a specific employee")
    public ResponseEntity<List<ProjectDto>> getProjectsByEmployee(
            @Parameter(description = "Employee identifier", required = true, example = "1")
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(projectService.getProjectsByEmployee(employeeId));
    }

    @GetMapping("/manager/{managerId}")
    @Operation(summary = "Manager projects", description = "Finds all projects managed by a specific employee")
    public ResponseEntity<List<ProjectDto>> getProjectsByManager(
            @Parameter(description = "Manager employee identifier", required = true, example = "1")
            @PathVariable Long managerId) {
        return ResponseEntity.ok(projectService.getProjectsByManager(managerId));
    }

    @GetMapping("/active")
    @Operation(summary = "Active projects", description = "Finds projects active within a specified date range")
    public ResponseEntity<List<ProjectDto>> getActiveProjects(
            @Parameter(description = "Start date filter (YYYY-MM-DD)", required = true, example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date filter (YYYY-MM-DD)", required = true, example = "2024-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(projectService.getActiveProjects(startDate, endDate));
    }

    @GetMapping("/summary/overall")
    @Operation(summary = "Overall project statistics", description = "Returns key metrics for the main dashboard view")
    public ResponseEntity<ProjectSummaryDto> getOverallProjectSummary() {
        return ResponseEntity.ok(projectService.getOverallSummary());
    }

    @GetMapping("/summary/upcoming-deadlines")
    @Operation(summary = "Projects with approaching deadlines", description = "Finds projects with an end date in the next N days (e.g., 30 days)")
    public ResponseEntity<List<ProjectDto>> getUpcomingDeadlines(
            @RequestParam(defaultValue = "30") int daysAhead) {
        return ResponseEntity.ok(projectService.getProjectsEndingSoon(daysAhead));
    }

    @GetMapping("/analysis/status-breakdown")
    @Operation(summary = "Project count by status", description = "Returns a list of counts for each ProjectStatus")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByStatus() {
        return ResponseEntity.ok(projectService.getProjectsCountByStatus());
    }

    @GetMapping("/analysis/service-type-breakdown")
    @Operation(summary = "Project count by service type", description = "Returns a list of counts for each ProjectServiceType")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByServiceType() {
        return ResponseEntity.ok(projectService.getProjectsCountByServiceType());
    }

    @GetMapping("/analysis/location-breakdown")
    @Operation(summary = "Project count by location", description = "Returns project counts grouped by location")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByLocation() {
        return ResponseEntity.ok(projectService.getProjectsCountByLocation());
    }

    @GetMapping("/resource/top-utilization")
    @Operation(summary = "Top utilized employees", description = "Returns a list of employees assigned to the most ACTIVE projects")
    public ResponseEntity<List<EmployeeUtilizationDto>> getTopUtilizedEmployees(
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(projectService.getTopUtilizedEmployees(limit));
    }

    @GetMapping("/resource/manager-load")
    @Operation(summary = "Project load by manager", description = "Returns the count of ACTIVE projects managed by each Project Manager")
    public ResponseEntity<List<CountByGroupDto>> getManagerProjectLoad() {
        return ResponseEntity.ok(projectService.getManagerProjectLoad());
    }

    @PostMapping("/public/cards/filter")
    @Operation(summary = "Filter public project cards", description = "Advanced filtering of projects by multiple criteria")
    public ResponseEntity<List<ProjectCardDto>> filterPublicProjectCards(@RequestBody ProjectFilterDto filter) {
        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/cards/filter")
    @Operation(summary = "Filter project cards (GET)", description = "Filtering using query parameters as an alternative to POST")
    public ResponseEntity<List<ProjectCardDto>> filterPublicProjectCardsGet(
            @Parameter(description = "Statuses (comma-separated)", example = "ACTIVE,PLANNED")
            @RequestParam(required = false) List<ProjectStatus> statuses,

            @Parameter(description = "Service types (comma-separated)")
            @RequestParam(required = false) List<ProjectServiceType> serviceTypes,

            @Parameter(description = "Priorities (comma-separated)")
            @RequestParam(required = false) List<ProjectPriority> priorities,

            @Parameter(description = "Technologies (comma-separated)", example = "SIEMENS_S7,ALLEN_BRADLEY")
            @RequestParam(required = false) List<ProjectTechnology> technologies,

            @Parameter(description = "Location ID", example = "1")
            @RequestParam(required = false) Long locationId,

            @Parameter(description = "Location name", example = "Poznań")
            @RequestParam(required = false) String locationName,

            @Parameter(description = "Country", example = "Poland")
            @RequestParam(required = false) String country,

            @Parameter(description = "City", example = "Poznań")
            @RequestParam(required = false) String city,

            @Parameter(description = "Start date from", example = "2024-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,

            @Parameter(description = "Start date to", example = "2024-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo,

            @Parameter(description = "End date from", example = "2024-06-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateFrom,

            @Parameter(description = "End date to", example = "2025-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateTo,

            @Parameter(description = "Search phrase")
            @RequestParam(required = false) String searchQuery,

            @Parameter(description = "Minimum team size")
            @RequestParam(required = false) Integer minTeamSize,

            @Parameter(description = "Maximum team size")
            @RequestParam(required = false) Integer maxTeamSize,

            @Parameter(description = "Sort by field", example = "startDate")
            @RequestParam(required = false, defaultValue = "startDate") String sortBy,

            @Parameter(description = "Sort direction", example = "desc")
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {

        ProjectFilterDto filter = ProjectFilterDto.builder()
                .statuses(statuses)
                .serviceTypes(serviceTypes)
                .priorities(priorities)
                .technologies(technologies)
                .locationId(locationId)
                .locationName(locationName)
                .country(country)
                .city(city)
                .startDateFrom(startDateFrom)
                .startDateTo(startDateTo)
                .endDateFrom(endDateFrom)
                .endDateTo(endDateTo)
                .searchQuery(searchQuery)
                .minTeamSize(minTeamSize)
                .maxTeamSize(maxTeamSize)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/technologies")
    @Operation(summary = "Get available technologies", description = "Returns a list of all technologies used in projects (for autocomplete/filters)")
    public ResponseEntity<List<String>> getAvailableTechnologies() {
        return ResponseEntity.ok(projectService.getAvailableTechnologies());
    }

    @GetMapping("/public/locations")
    @Operation(summary = "Get available locations", description = "Returns a list of all project locations (for autocomplete/map)")
    public ResponseEntity<List<String>> getAvailableLocations() {
        return ResponseEntity.ok(projectService.getAvailableLocations());
    }

    @GetMapping("/public/filter-stats")
    @Operation(summary = "Filter statistics", description = "Returns project counts for each filter option")
    public ResponseEntity<FilterStatsDto> getFilterStatistics() {
        return ResponseEntity.ok(projectService.getFilterStatistics());
    }

    @GetMapping("/public/cards/by-technology")
    @Operation(summary = "Projects by technology", description = "Quick filter by a single technology (for top buttons)")
    public ResponseEntity<List<ProjectCardDto>> getProjectsByTechnology(
            @Parameter(description = "Technology name", example = "SIEMENS_S7")
            @RequestParam String technology) {

        ProjectFilterDto filter = ProjectFilterDto.builder()
                .technologies(List.of(ProjectTechnology.valueOf(technology.toUpperCase().replace("-", "_"))))
                .sortBy("startDate")
                .sortDirection("desc")
                .build();

        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/cards/map-data")
    @Operation(summary = "Map project data", description = "Returns projects with coordinates for map display")
    public ResponseEntity<List<ProjectMapPointDto>> getProjectsMapData(
            @Parameter(description = "Optional status filter")
            @RequestParam(required = false) List<ProjectStatus> statuses) {
        return ResponseEntity.ok(projectService.getProjectsForMap(statuses));
    }
}
