package com.automationcompany.project.controller;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
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
    @Operation(summary = "Get all projects", description = "Retrieves complete list of all projects")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project details", description = "Retrieves detailed information about specific project")
    public ResponseEntity<ProjectDto> getProjectById(
            @PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/{id}/with-employees")
    @Operation(summary = "Get project with team", description = "Retrieves project details including assigned employees and manager")
    public ResponseEntity<ProjectWithEmployeesDto> getProjectWithEmployees(
            @PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectWithEmployees(id));
    }

    @PostMapping
    @Operation(summary = "Create project", description = "Creates new project with initial configuration and team")
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody @Schema(description = "Project creation request")
            ProjectCreateDto createDto) {
        ProjectDto createdProject = projectService.createProject(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project", description = "Updates project details, status, dates or team assignments")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody
            ProjectUpdateDto updateDto) {
        return ResponseEntity.ok(projectService.updateProject(id, updateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project", description = "Permanently removes project and all its assignments")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/employees")
    @Operation(summary = "Assign team members", description = "Adds employees to project team")
    public ResponseEntity<ProjectDto> assignEmployees(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "List of employee IDs to assign", example = "[2, 3, 5]")
            @RequestBody Set<Long> employeeIds) {
        return ResponseEntity.ok(projectService.assignEmployees(id, employeeIds));
    }

    @DeleteMapping("/{id}/employees")
    @Operation(summary = "Remove team members", description = "Removes employees from project team")
    public ResponseEntity<ProjectDto> removeEmployees(
            @Parameter(description = "Unique project identifier", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "List of employee IDs to remove", example = "[2, 3]")
            @RequestBody Set<Long> employeeIds) {
        return ResponseEntity.ok(projectService.removeEmployees(id, employeeIds));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filter projects by status", description = "Retrieves projects with specific status")
    public ResponseEntity<List<ProjectDto>> getProjectsByStatus(
            @Parameter(description = "Project status filter", required = true, example = "ACTIVE")
            @PathVariable ProjectStatus status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Employee projects", description = "Finds all projects assigned to specific employee")
    public ResponseEntity<List<ProjectDto>> getProjectsByEmployee(
            @Parameter(description = "Employee identifier", required = true, example = "1")
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(projectService.getProjectsByEmployee(employeeId));
    }

    @GetMapping("/manager/{managerId}")
    @Operation(summary = "Manager projects", description = "Finds all projects managed by specific employee")
    public ResponseEntity<List<ProjectDto>> getProjectsByManager(
            @Parameter(description = "Manager employee identifier", required = true, example = "1")
            @PathVariable Long managerId) {
        return ResponseEntity.ok(projectService.getProjectsByManager(managerId));
    }

    @GetMapping("/active")
    @Operation(summary = "Active projects", description = "Finds projects active within specified date range")
    public ResponseEntity<List<ProjectDto>> getActiveProjects(
            @Parameter(description = "Start date filter (YYYY-MM-DD)", required = true, example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date filter (YYYY-MM-DD)", required = true, example = "2024-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(projectService.getActiveProjects(startDate, endDate));
    }

    @GetMapping("/summary/overall")
    @Operation(summary = "Overall project statistics", description = "Returns key metrics for the main dashboard view.")
    public ResponseEntity<ProjectSummaryDto> getOverallProjectSummary() {
        return ResponseEntity.ok(projectService.getOverallSummary());
    }

    @GetMapping("/summary/upcoming-deadlines")
    @Operation(summary = "Projects with approaching deadlines", description = "Finds projects with an end date in the next N days (e.g., 30 days).")
    public ResponseEntity<List<ProjectDto>> getUpcomingDeadlines(
            @RequestParam(defaultValue = "30") int daysAhead) {
        return ResponseEntity.ok(projectService.getProjectsEndingSoon(daysAhead));
    }

    @GetMapping("/analysis/status-breakdown")
    @Operation(summary = "Project count by status", description = "Returns a map or list of counts for each ProjectStatus.")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByStatus() {
        return ResponseEntity.ok(projectService.getProjectsCountByStatus());
    }

    @GetMapping("/analysis/service-type-breakdown")
    @Operation(summary = "Project count by service type", description = "Returns a map or list of counts for each ProjectServiceType.")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByServiceType() {
        return ResponseEntity.ok(projectService.getProjectsCountByServiceType());
    }

    @GetMapping("/analysis/location-breakdown")
    @Operation(summary = "Project count by location", description = "Returns project counts grouped by location.")
    public ResponseEntity<List<CountByGroupDto>> getProjectsCountByLocation() {
        return ResponseEntity.ok(projectService.getProjectsCountByLocation());
    }

    @GetMapping("/resource/top-utilization")
    @Operation(summary = "Top utilized employees", description = "Returns a list of employees assigned to the most ACTIVE projects.")
    public ResponseEntity<List<EmployeeUtilizationDto>> getTopUtilizedEmployees(
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(projectService.getTopUtilizedEmployees(limit));
    }

    @GetMapping("/resource/manager-load")
    @Operation(summary = "Project load by manager", description = "Returns the count of ACTIVE projects managed by each Project Manager.")
    public ResponseEntity<List<CountByGroupDto>> getManagerProjectLoad() {
        return ResponseEntity.ok(projectService.getManagerProjectLoad());
    }

    @PostMapping("/public/cards/filter")
    @Operation(
            summary = "Filtruj publiczne karty projektów",
            description = "Zaawansowane filtrowanie projektów według wielu kryteriów"
    )
    public ResponseEntity<List<ProjectCardDto>> filterPublicProjectCards(
            @RequestBody ProjectFilterDto filter) {
        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/cards/filter")
    @Operation(
            summary = "Filtruj karty projektów (GET)",
            description = "Filtrowanie za pomocą query parameters - alternatywa dla POST"
    )
    public ResponseEntity<List<ProjectCardDto>> filterPublicProjectCardsGet(
            @Parameter(description = "Statusy (oddzielone przecinkami)", example = "ACTIVE,PLANNED")
            @RequestParam(required = false) List<ProjectStatus> statuses,

            @Parameter(description = "Typy usług (oddzielone przecinkami)")
            @RequestParam(required = false) List<ProjectServiceType> serviceTypes,

            @Parameter(description = "Priorytety (oddzielone przecinkami)")
            @RequestParam(required = false) List<ProjectPriority> priorities,

            @Parameter(description = "Technologie (oddzielone przecinkami)", example = "Siemens,Allen Bradley")
            @RequestParam(required = false) List<String> technologies,

            @Parameter(description = "Lokalizacja", example = "Poznań")
            @RequestParam(required = false) String location,

            @Parameter(description = "Data rozpoczęcia od", example = "2024-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,

            @Parameter(description = "Data rozpoczęcia do", example = "2024-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo,

            @Parameter(description = "Fraza wyszukiwania")
            @RequestParam(required = false) String searchQuery,

            @Parameter(description = "Minimalny rozmiar zespołu")
            @RequestParam(required = false) Integer minTeamSize,

            @Parameter(description = "Maksymalny rozmiar zespołu")
            @RequestParam(required = false) Integer maxTeamSize,

            @Parameter(description = "Sortowanie według", example = "startDate")
            @RequestParam(required = false, defaultValue = "startDate") String sortBy,

            @Parameter(description = "Kierunek sortowania", example = "desc")
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {

        ProjectFilterDto filter = ProjectFilterDto.builder()
                .statuses(statuses)
                .serviceTypes(serviceTypes)
                .priorities(priorities)
                .technologies(technologies)
                .location(location)
                .startDateFrom(startDateFrom)
                .startDateTo(startDateTo)
                .searchQuery(searchQuery)
                .minTeamSize(minTeamSize)
                .maxTeamSize(maxTeamSize)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/technologies")
    @Operation(
            summary = "Pobierz dostępne technologie",
            description = "Lista wszystkich technologii używanych w projektach (do autocomplete/filtrów)"
    )
    public ResponseEntity<List<String>> getAvailableTechnologies() {
        return ResponseEntity.ok(projectService.getAvailableTechnologies());
    }

    @GetMapping("/public/locations")
    @Operation(
            summary = "Pobierz dostępne lokalizacje",
            description = "Lista wszystkich lokalizacji projektów (do autocomplete/mapy)"
    )
    public ResponseEntity<List<String>> getAvailableLocations() {
        return ResponseEntity.ok(projectService.getAvailableLocations());
    }

    @GetMapping("/public/filter-stats")
    @Operation(
            summary = "Statystyki dla filtrów",
            description = "Zwraca liczby projektów dla poszczególnych opcji filtrowania"
    )
    public ResponseEntity<FilterStatsDto> getFilterStatistics() {
        return ResponseEntity.ok(projectService.getFilterStatistics());
    }

    @GetMapping("/public/cards/by-technology")
    @Operation(
            summary = "Projekty według technologii",
            description = "Szybkie filtrowanie po jednej technologii (dla przycisków górnych)"
    )
    public ResponseEntity<List<ProjectCardDto>> getProjectsByTechnology(
            @Parameter(description = "Nazwa technologii", example = "Siemens S7-1500")
            @RequestParam String technology) {

        ProjectFilterDto filter = ProjectFilterDto.builder()
                .technologies(List.of(technology))
                .sortBy("startDate")
                .sortDirection("desc")
                .build();

        return ResponseEntity.ok(projectService.filterPublicProjectCards(filter));
    }

    @GetMapping("/public/cards/map-data")
    @Operation(
            summary = "Dane projektów dla mapy",
            description = "Zwraca projekty z koordynatami dla wyświetlenia na mapie"
    )
    public ResponseEntity<List<ProjectMapPointDto>> getProjectsMapData(
            @Parameter(description = "Opcjonalne filtrowanie")
            @RequestParam(required = false) List<ProjectStatus> statuses) {
        return ResponseEntity.ok(projectService.getProjectsForMap(statuses));
    }
}