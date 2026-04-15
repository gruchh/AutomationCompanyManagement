package com.automationcompany.project.controller;

import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/cards/search")
    @Operation(
            summary = "Search and filter projects",
            description = "Advanced filtering via request body (recommended)"
    )
    public ResponseEntity<List<ProjectCardDto>> searchProjectCards(
            @RequestBody ProjectFilterDto filter
    ) {
        return ResponseEntity.ok(projectService.filterProjects(filter));
    }
}