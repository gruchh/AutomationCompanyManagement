package com.automationcompany.project.controller;

import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectUpdateDto;
import com.automationcompany.project.model.dto.ProjectWithEmployeesDto;
import com.automationcompany.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/{id}/with-employees")
    public ResponseEntity<ProjectWithEmployeesDto> getProjectWithEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectWithEmployees(id));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectCreateDto createDto) {
        ProjectDto createdProject = projectService.createProject(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateDto updateDto) {
        return ResponseEntity.ok(projectService.updateProject(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/employees")
    public ResponseEntity<ProjectDto> assignEmployees(
            @PathVariable Long id,
            @RequestBody Set<Long> employeeIds) {
        return ResponseEntity.ok(projectService.assignEmployees(id, employeeIds));
    }

    @DeleteMapping("/{id}/employees")
    public ResponseEntity<ProjectDto> removeEmployees(
            @PathVariable Long id,
            @RequestBody Set<Long> employeeIds) {
        return ResponseEntity.ok(projectService.removeEmployees(id, employeeIds));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectDto>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectService.getProjectsByEmployee(employeeId));
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(projectService.getProjectsByManager(managerId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProjectDto>> getActiveProjects(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(projectService.getActiveProjects(startDate, endDate));
    }
}