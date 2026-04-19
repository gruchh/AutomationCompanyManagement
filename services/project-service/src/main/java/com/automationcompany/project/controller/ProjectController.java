package com.automationcompany.project.controller;

import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/public/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/cards/search", produces = "application/json")
    public ResponseEntity<List<ProjectCardDto>> searchProjectCards(
            @RequestBody ProjectFilterDto filter
    ) {
        return ResponseEntity.ok(projectService.filterProjects(filter));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectCreateDto dto) {
        ProjectDto created = projectService.createProject(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}