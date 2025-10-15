package com.automationcompany.project.service;

import com.automationcompany.project.client.EmployeeClient;
import com.automationcompany.project.exception.ProjectNotFoundException;
import com.automationcompany.project.exception.DuplicateProjectCodeException;
import com.automationcompany.project.exception.InvalidEmployeeException;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeClient employeeClient;

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
            try {
                employees = employeeClient.getEmployeesByIds(new ArrayList<>(project.getEmployeeIds()));
            } catch (Exception e) {
                log.error("Error fetching employees for project {}: {}", id, e.getMessage());
            }
        }

        EmployeeDto projectManager = null;
        if (project.getProjectManagerId() != null) {
            try {
                projectManager = employeeClient.getEmployeeById(project.getProjectManagerId());
            } catch (Exception e) {
                log.error("Error fetching project manager {}: {}", project.getProjectManagerId(), e.getMessage());
            }
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
            try {
                List<EmployeeDto> employees = employeeClient.getEmployeesByIds(new ArrayList<>(employeeIds));
                if (employees.size() != employeeIds.size()) {
                    throw new InvalidEmployeeException("Some employee IDs are invalid");
                }
            } catch (Exception e) {
                log.error("Error validating employees: {}", e.getMessage());
                throw new InvalidEmployeeException("Unable to validate employees: " + e.getMessage());
            }
        }
    }

    private void validateEmployee(Long employeeId) {
        try {
            EmployeeDto employee = employeeClient.getEmployeeById(employeeId);
            if (employee == null) {
                throw new InvalidEmployeeException("Employee not found with id: " + employeeId);
            }
        } catch (Exception e) {
            log.error("Error validating employee {}: {}", employeeId, e.getMessage());
            throw new InvalidEmployeeException("Unable to validate employee: " + e.getMessage());
        }
    }
}