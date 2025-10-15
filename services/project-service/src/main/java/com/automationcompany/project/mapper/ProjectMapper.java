package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.*;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectMapper {

    @Mapping(target = "employeeIds", source = "employeeIds")
    ProjectDto toDto(Project project);
    List<ProjectDto> toDtoList(List<Project> projects);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Project toEntity(ProjectCreateDto createDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromDto(ProjectUpdateDto updateDto, @MappingTarget Project project);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "projectManager", ignore = true)
    ProjectWithEmployeesDto toWithEmployeesDto(Project project);

    default ProjectWithEmployeesDto toWithEmployeesDto(Project project, List<EmployeeDto> employees, EmployeeDto projectManager) {
        ProjectWithEmployeesDto dto = toWithEmployeesDto(project);

        Optional.ofNullable(employees)
                .map(HashSet::new)
                .ifPresent(dto::setEmployees);

        dto.setProjectManager(projectManager);
        return dto;
    }
}