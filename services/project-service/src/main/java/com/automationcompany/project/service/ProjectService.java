package com.automationcompany.project.service;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.exception.DuplicateProjectCodeException;
import com.automationcompany.project.exception.InvalidEmployeeException;
import com.automationcompany.project.exception.ProjectNotFoundException;
import com.automationcompany.project.mapper.ProjectCardMapper;
import com.automationcompany.project.mapper.ProjectMapPointMapper;
import com.automationcompany.project.mapper.ProjectMapper;
import com.automationcompany.project.model.*;
import com.automationcompany.project.model.dto.*;
import com.automationcompany.project.repository.ProjectRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectCardMapper projectCardMapper;
    private final ProjectMapPointMapper projectMapPointMapper;
    private final EmployeeWebClient employeeClient;

    public List<ProjectDto> getAllProjects() {
        log.debug("Fetching all projects");
        return projectMapper.toDtoList(projectRepository.findAll());
    }

    public ProjectDto getProjectById(Long id) {
        log.debug("Fetching project with id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        return projectMapper.toDto(project);
    }

    public ProjectWithEmployeesDto getProjectWithEmployees(Long id) {
        log.debug("Fetching project with employees for id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        List<EmployeeReadDto> employees = Collections.emptyList();
        if (project.getEmployeeIds() != null && !project.getEmployeeIds().isEmpty()) {
            employees = employeeClient.getEmployeesByIds(new ArrayList<>(project.getEmployeeIds()));
            employees = employees != null ? employees : Collections.emptyList();
        }

        EmployeeReadDto projectManager = null;
        if (project.getProjectManagerId() != null) {
            Optional<EmployeeReadDto> managerOpt = employeeClient.getEmployeeById(project.getProjectManagerId());
            projectManager = managerOpt.orElse(null);
        }

        return projectMapper.toWithEmployeesDto(project, employees, projectManager);
    }

    @Transactional
    public ProjectDto createProject(ProjectCreateDto createDto) {
        log.debug("Creating new project: {}", createDto.getName());

        if (createDto.getCode() != null && projectRepository.existsByCode(createDto.getCode())) {
            throw new DuplicateProjectCodeException("Project with code " + createDto.getCode() + " already exists");
        }

        validateEmployees(createDto.getEmployeeIds(), createDto.getProjectManagerId());

        Project project = projectMapper.toEntity(createDto);
        Project savedProject = projectRepository.save(project);

        log.info("Project created successfully with id: {}", savedProject.getId());
        return projectMapper.toDto(savedProject);
    }

    @Transactional
    public ProjectDto updateProject(Long id, ProjectUpdateDto updateDto) {
        log.debug("Updating project with id: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        if (updateDto.getCode() != null && !updateDto.getCode().equals(project.getCode())) {
            if (projectRepository.existsByCode(updateDto.getCode())) {
                throw new DuplicateProjectCodeException("Project with code " + updateDto.getCode() + " already exists");
            }
        }

        if (updateDto.getProjectManagerId() != null) {
            validateEmployee(updateDto.getProjectManagerId());
        }

        projectMapper.updateEntityFromDto(updateDto, project);
        Project updatedProject = projectRepository.save(project);

        log.info("Project updated successfully with id: {}", id);
        return projectMapper.toDto(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        log.debug("Deleting project with id: {}", id);

        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }

        projectRepository.deleteById(id);
        log.info("Project deleted successfully with id: {}", id);
    }

    @Transactional
    public ProjectDto assignEmployees(Long projectId, List<Long> employeeIds) {
        log.debug("Assigning employees {} to project {}", employeeIds, projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        validateEmployees(employeeIds, null);

        if (project.getEmployeeIds() == null) {
            project.setEmployeeIds(new HashSet<>());
        }
        project.getEmployeeIds().addAll(employeeIds);

        Project updatedProject = projectRepository.save(project);
        log.info("Employees assigned to project {}", projectId);
        return projectMapper.toDto(updatedProject);
    }

    @Transactional
    public ProjectDto removeEmployees(Long projectId, Set<Long> employeeIds) {
        log.debug("Removing employees {} from project {}", employeeIds, projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        if (project.getEmployeeIds() != null) {
            project.getEmployeeIds().removeAll(employeeIds);
        }

        Project updatedProject = projectRepository.save(project);
        log.info("Employees removed from project {}", projectId);
        return projectMapper.toDto(updatedProject);
    }

    public List<ProjectDto> getProjectsByStatus(ProjectStatus status) {
        log.debug("Fetching projects with status: {}", status);
        return projectMapper.toDtoList(projectRepository.findByStatus(status));
    }

    public List<ProjectDto> getProjectsByEmployee(Long employeeId) {
        log.debug("Fetching projects for employee: {}", employeeId);
        return projectMapper.toDtoList(projectRepository.findByEmployeeId(employeeId));
    }

    public List<ProjectDto> getProjectsByManager(Long managerId) {
        log.debug("Fetching projects for manager: {}", managerId);
        return projectMapper.toDtoList(projectRepository.findByProjectManagerId(managerId));
    }

    public List<ProjectDto> getActiveProjects(LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching active projects between {} and {}", startDate, endDate);
        return projectMapper.toDtoList(projectRepository.findActiveProjectsBetween(startDate, endDate));
    }

    private void validateEmployees(List<Long> employeeIds, Long projectManagerId) {
        if (projectManagerId != null) {
            validateEmployee(projectManagerId);
        }

        if (employeeIds != null && !employeeIds.isEmpty()) {
            List<EmployeeReadDto> employees = employeeClient.getEmployeesByIds(new ArrayList<>(employeeIds));
            if (employees == null || employees.size() != employeeIds.size()) {
                throw new InvalidEmployeeException("Some employee IDs are invalid");
            }
        }
    }

    private void validateEmployee(Long employeeId) {
        Optional<EmployeeReadDto> employeeOpt = employeeClient.getEmployeeById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new InvalidEmployeeException("Employee not found with id: " + employeeId);
        }
    }

    public ProjectSummaryDto getOverallSummary() {
        Long total = projectRepository.count();
        Long active = projectRepository.countByStatus(ProjectStatus.IN_PROGRESS);
        Long completed = projectRepository.countByStatus(ProjectStatus.COMPLETED);
        Long pastDeadline = projectRepository.countProjectsPastDeadline();
        Double avgDuration = projectRepository.getAverageCompletedProjectDuration();

        return new ProjectSummaryDto(total, active, completed, pastDeadline, avgDuration);
    }

    public List<CountByGroupDto> getProjectsCountByStatus() {
        return projectRepository.countProjectsByStatus();
    }

    public List<CountByGroupDto> getProjectsCountByServiceType() {
        return projectRepository.countProjectsByServiceType();
    }

    public List<CountByGroupDto> getProjectsCountByLocation() {
        return projectRepository.countProjectsByLocation();
    }

    public List<CountByGroupDto> getManagerProjectLoad() {
        return projectRepository.countActiveProjectsByManager();
    }

    public List<EmployeeUtilizationDto> getTopUtilizedEmployees(int limit) {
        List<Long> topEmployeeIds = projectRepository.findAll().stream()
                .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS)
                .flatMap(p -> p.getEmployeeIds().stream())
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(limit)
                .map(java.util.Map.Entry::getKey)
                .collect(Collectors.toList());


        List<EmployeeReadDto> employees = employeeClient.getEmployeesByIds(topEmployeeIds);

        return employees.stream()
                .map(e -> {
                    Long projectCount = projectRepository.findAll().stream()
                            .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS)
                            .filter(p -> p.getEmployeeIds().contains(e.getId()))
                            .count();

                    return new EmployeeUtilizationDto(
                            e.getId(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getPositionLevel(),
                            projectCount
                    );
                })
                .sorted((e1, e2) -> e2.activeProjectCount().compareTo(e1.activeProjectCount()))
                .toList();
    }

    public List<ProjectDto> getProjectsEndingSoon(int daysAhead) {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(daysAhead);

        List<Project> projects = projectRepository.findByEndDateBetweenAndStatusIsNot(
                start,
                end,
                ProjectStatus.COMPLETED
        );

        return projectMapper.toDtoList(projects);
    }

    public List<ProjectCardDto> filterPublicProjectCards(ProjectFilterDto filter) {
        Specification<Project> spec = buildProjectSpecification(filter);
        List<Project> projects = projectRepository.findAll(spec);

        List<ProjectCardDto> cards = projects.stream()
                .map(projectCardMapper::toDto)
                .collect(Collectors.toList());

        if (filter.getTechnologies() != null && !filter.getTechnologies().isEmpty()) {
            Set<String> filterTechs = filter.getTechnologies().stream()
                    .map(ProjectTechnology::name)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            cards = cards.stream()
                    .filter(card -> card.getTechnologies().stream()
                            .map(ProjectTechnology::name)
                            .map(String::toLowerCase)
                            .anyMatch(filterTechs::contains))
                    .collect(Collectors.toList());
        }

        if (filter.getMinTeamSize() != null) {
            cards = cards.stream()
                    .filter(card -> card.getTeamSize() >= filter.getMinTeamSize())
                    .collect(Collectors.toList());
        }
        if (filter.getMaxTeamSize() != null) {
            cards = cards.stream()
                    .filter(card -> card.getTeamSize() <= filter.getMaxTeamSize())
                    .collect(Collectors.toList());
        }

        cards = applySorting(cards.stream(), filter)
                .collect(Collectors.toList());

        return cards;
    }

    private Specification<Project> buildProjectSpecification(ProjectFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
                predicates.add(root.get("status").in(filter.getStatuses()));
            }

            if (filter.getServiceTypes() != null && !filter.getServiceTypes().isEmpty()) {
                predicates.add(root.get("serviceType").in(filter.getServiceTypes()));
            }

            if (filter.getPriorities() != null && !filter.getPriorities().isEmpty()) {
                predicates.add(root.get("priority").in(filter.getPriorities()));
            }

            if (filter.getLocationId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("location").get("id"),
                        filter.getLocationId()
                ));
            }

            if (filter.getLocationName() != null && !filter.getLocationName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location").get("name")),
                        "%" + filter.getLocationName().toLowerCase() + "%"
                ));
            }

            if (filter.getCountry() != null && !filter.getCountry().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location").get("country")),
                        "%" + filter.getCountry().toLowerCase() + "%"
                ));
            }

            if (filter.getCity() != null && !filter.getCity().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location").get("city")),
                        "%" + filter.getCity().toLowerCase() + "%"
                ));
            }

            if (filter.getStartDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDate"), filter.getStartDateFrom()
                ));
            }
            if (filter.getStartDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("startDate"), filter.getStartDateTo()
                ));
            }

            if (filter.getEndDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("endDate"), filter.getEndDateFrom()
                ));
            }
            if (filter.getEndDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDate"), filter.getEndDateTo()
                ));
            }

            if (filter.getSearchQuery() != null && !filter.getSearchQuery().trim().isEmpty()) {
                String searchPattern = "%" + filter.getSearchQuery().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), searchPattern
                );
                Predicate descriptionPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")), searchPattern
                );
                predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Stream<ProjectCardDto> applySorting(Stream<ProjectCardDto> stream, ProjectFilterDto filter) {
        String sortBy = filter.getSortBy() != null ? filter.getSortBy() : "startDate";
        boolean ascending = "asc".equalsIgnoreCase(filter.getSortDirection());

        Comparator<ProjectCardDto> comparator = switch (sortBy.toLowerCase()) {
            case "name" -> Comparator.comparing(ProjectCardDto::getName);
            case "teamsize" -> Comparator.comparing(ProjectCardDto::getTeamSize);
            case "status" -> Comparator.comparing(card -> card.getStatus().toString());
            case "location" -> Comparator.comparing(
                    ProjectCardDto::getLocation,
                    Comparator.nullsLast(String::compareTo)
            );
            default -> Comparator.comparing(
                    ProjectCardDto::getStartDate,
                    Comparator.nullsLast(LocalDate::compareTo)
            );
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return stream.sorted(comparator);
    }

    public List<String> getAvailableTechnologies() {
        return projectRepository.findAll().stream()
                .flatMap(project -> project.getTechnologies().stream()
                        .map(Enum::name))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getAvailableLocations() {
        return projectRepository.findAll().stream()
                .map(Project::getLocation)
                .filter(location -> location != null)
                .map(location -> location.getName())
                .filter(name -> name != null && !name.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public FilterStatsDto getFilterStatistics() {
        List<Project> allProjects = projectRepository.findAll();

        Map<ProjectStatus, Long> statusCounts = allProjects.stream()
                .collect(Collectors.groupingBy(Project::getStatus, Collectors.counting()));

        Map<ProjectServiceType, Long> serviceTypeCounts = allProjects.stream()
                .collect(Collectors.groupingBy(Project::getServiceType, Collectors.counting()));

        Map<String, Long> locationCounts = allProjects.stream()
                .map(Project::getLocation)
                .filter(location -> location != null)
                .map(Location::getName)
                .filter(name -> name != null && !name.trim().isEmpty())
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return FilterStatsDto.builder()
                .statusCounts(statusCounts)
                .serviceTypeCounts(serviceTypeCounts)
                .locationCounts(locationCounts)
                .totalProjects(allProjects.size())
                .build();
    }

    public List<ProjectMapPointDto> getProjectsForMap(List<ProjectStatus> statuses) {
        List<Project> projects;

        if (statuses == null || statuses.isEmpty()) {
            projects = projectRepository.findAllWithLocation();
        } else {
            projects = projectRepository.findByStatusInWithLocation(statuses);
        }

        return projectMapPointMapper.toDtoList(projects);
    }
}