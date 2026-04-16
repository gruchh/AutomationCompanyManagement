package com.automationcompany.project.controller;

import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}