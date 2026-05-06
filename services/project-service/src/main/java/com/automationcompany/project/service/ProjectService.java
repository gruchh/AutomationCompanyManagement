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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<ProjectDto> createProject(ProjectCreateDto dto) {
        return Mono.zip(validateManager(dto.getProjectManagerId()), validateEmployees(dto.getEmployeeIds()))
                .then(Mono.fromCallable(() -> {
                    Project project = projectMapper.toEntity(dto);
                    if (dto.getEmployeeIds() != null) {
                        project.setEmployeeIds(new HashSet<>(dto.getEmployeeIds()));
                    }
                    return projectMapper.toDto(projectRepository.save(project));
                }));
    }

    private Mono<Boolean> validateManager(Long managerId) {
        if (managerId == null) return Mono.just(true);
        return employeeWebClient.getEmployeeById(managerId)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException("Project manager not found: " + managerId)))
                .thenReturn(true);
    }

    private Mono<Boolean> validateEmployees(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Mono.just(true);
        return employeeWebClient.getEmployeesByIds(ids)
                .map(EmployeeReadDto::getId)
                .collect(Collectors.toSet())
                .flatMap(foundIds -> {
                    List<Long> missing = ids.stream().filter(id -> !foundIds.contains(id)).toList();
                    return missing.isEmpty()
                            ? Mono.just(true)
                            : Mono.error(new EmployeeNotFoundException("Employees not found: " + missing));
                });
    }
}