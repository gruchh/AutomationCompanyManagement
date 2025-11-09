package com.automationcompany.project.mapper;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.ProjectCreateDto;
import com.automationcompany.project.model.dto.ProjectDto;
import com.automationcompany.project.model.dto.ProjectUpdateDto;
import com.automationcompany.project.model.dto.ProjectWithEmployeesDto;
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

    default ProjectWithEmployeesDto toWithEmployeesDto(Project project, List<EmployeeReadDto> employees, EmployeeReadDto projectManager) {
        ProjectWithEmployeesDto dto = toWithEmployeesDto(project);

        Optional.ofNullable(employees)
                .map(HashSet::new)
                .ifPresent(dto::setEmployees);

        dto.setProjectManager(projectManager);
        return dto;
    }
}