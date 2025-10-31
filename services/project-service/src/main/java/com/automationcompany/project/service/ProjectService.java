package com.automationcompany.project.service;

import com.automationcompany.project.client.EmployeeWebClient;
import com.automationcompany.project.exception.DuplicateProjectCodeException;
import com.automationcompany.project.exception.InvalidEmployeeException;
import com.automationcompany.project.exception.ProjectNotFoundException;
import com.automationcompany.project.mapper.ProjectMapper;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.dto.*;
import com.automationcompany.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
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

        List<EmployeeDto> employees = Collections.emptyList();
        if (project.getEmployeeIds() != null && !project.getEmployeeIds().isEmpty()) {
            employees = employeeClient.getEmployeesByIds(new ArrayList<>(project.getEmployeeIds()));
            employees = employees != null ? employees : Collections.emptyList();
        }

        EmployeeDto projectManager = null;
        if (project.getProjectManagerId() != null) {
            Optional<EmployeeDto> managerOpt = employeeClient.getEmployeeById(project.getProjectManagerId());
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
    public ProjectDto assignEmployees(Long projectId, Set<Long> employeeIds) {
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

    private void validateEmployees(Set<Long> employeeIds, Long projectManagerId) {
        if (projectManagerId != null) {
            validateEmployee(projectManagerId);
        }

        if (employeeIds != null && !employeeIds.isEmpty()) {
            List<EmployeeDto> employees = employeeClient.getEmployeesByIds(new ArrayList<>(employeeIds));
            if (employees == null || employees.size() != employeeIds.size()) {
                throw new InvalidEmployeeException("Some employee IDs are invalid");
            }
        }
    }

    private void validateEmployee(Long employeeId) {
        Optional<EmployeeDto> employeeOpt = employeeClient.getEmployeeById(employeeId);
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


        List<EmployeeDto> employees = employeeClient.getEmployeesByIds(topEmployeeIds);

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
}