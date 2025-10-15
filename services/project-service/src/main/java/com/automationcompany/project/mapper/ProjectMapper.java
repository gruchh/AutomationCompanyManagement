package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.*;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectMapper {

    ProjectDto toDto(Project project);
    List<ProjectDto> toDtoList(List<Project> projects);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "actualCost", constant = "0")
    Project toEntity(ProjectCreateDto createDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "employeeIds", ignore = true)
    void updateEntityFromDto(ProjectUpdateDto updateDto, @MappingTarget Project project);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "projectManager", ignore = true)
    ProjectWithEmployeesDto toWithEmployeesDto(Project project);

    default ProjectWithEmployeesDto toWithEmployeesDto(Project project, List<EmployeeDto> employees, EmployeeDto projectManager) {
        ProjectWithEmployeesDto dto = toWithEmployeesDto(project);
        if (employees != null) {
            dto.setEmployees(new HashSet<>(employees));
        }
        dto.setProjectManager(projectManager);
        return dto;
    }
}