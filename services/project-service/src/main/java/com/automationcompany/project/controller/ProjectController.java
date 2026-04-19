package com.automationcompany.project.controller;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeWebClient employeeWebClient;

    @PostMapping(value = "/cards/search", produces = "application/json")
    public ResponseEntity<List<ProjectCardDto>> searchProjectCards(
            @RequestBody ProjectFilterDto filter
    ) {
        return ResponseEntity.ok(projectService.filterProjects(filter));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeReadDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeWebClient.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(dto));
    }
}