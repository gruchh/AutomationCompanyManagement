package com.automationcompany.project.service;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.exception.EmployeeNotFoundException;
import com.automationcompany.project.mapper.ProjectMapper;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.ProjectCardDto;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectFilterDto;
import com.automationcompany.project.repository.ProjectRepository;
import com.automationcompany.project.repository.specification.ProjectSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeWebClient employeeWebClient;

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
            case "status" -> "status";
            case "location" -> "location.name";
            default -> "startDate";
        };
    }

    @Transactional
    public ProjectDto createProject(ProjectCreateDto dto) {

        if (dto.getProjectManagerId() != null) {
            employeeWebClient.getEmployeeById(dto.getProjectManagerId())
                    .orElseThrow(() -> new EmployeeNotFoundException(
                            "Project manager not found: " + dto.getProjectManagerId()));
        }

        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            List<EmployeeReadDto> found = employeeWebClient.getEmployeesByIds(dto.getEmployeeIds());

            Set<Long> foundIds = found.stream()
                    .map(EmployeeReadDto::getId)
                    .collect(Collectors.toSet());

            List<Long> missing = dto.getEmployeeIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            if (!missing.isEmpty()) {
                throw new EmployeeNotFoundException("Employees not found: " + missing);
            }
        }

        Project project = projectMapper.toEntity(dto);

        if (dto.getEmployeeIds() != null) {
            project.setEmployeeIds(new HashSet<>(dto.getEmployeeIds()));
        }

        return projectMapper.toDto(projectRepository.save(project));
    }
}