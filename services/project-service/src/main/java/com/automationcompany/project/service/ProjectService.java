package com.automationcompany.project.service;

import com.automationcompany.project.mapper.ProjectMapper;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.repository.ProjectRepository;
import com.automationcompany.project.repository.specification.ProjectSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectCardDto> filterProjects(ProjectFilterDto filter) {

        Specification<Project> spec = ProjectSpecification.build(filter);

        Sort sort = Sort.by(
                Sort.Direction.fromString(filter.getSortDirection()),
                mapSortField(filter.getSortBy())
        );

        return projectRepository.findAll(spec, sort)
                .stream()
                .map(projectMapper::toCardDto)
                .toList();
    }

    private String mapSortField(String sortBy) {
        return switch (sortBy) {
            case "name" -> "name";
            case "teamSize" -> "employees.size";
            case "status" -> "status";
            case "location" -> "location.name";
            default -> "startDate";
        };
    }
}