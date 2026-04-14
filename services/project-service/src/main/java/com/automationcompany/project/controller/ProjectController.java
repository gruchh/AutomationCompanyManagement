package com.automationcompany.project.controller;

import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/cards")
    @Operation(
            summary = "Search and filter projects",
            description = "One endpoint for all search needs, including live search"
    )
    public ResponseEntity<List<ProjectCardDto>> getProjectCards(
            @ModelAttribute ProjectFilterDto filter
    ) {
        return ResponseEntity.ok(projectService.filterProjects(filter));
    }
}