package com.automationcompany.project.mapper;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.*;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectMapper {

    @Mapping(target = "employeeIds", source = "employeeIds")
    @Mapping(target = "locationDto", source = "location")
    ProjectDto toDto(Project project);

    List<ProjectDto> toDtoList(List<Project> projects);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "location", source = "locationDto")
    Project toEntity(ProjectCreateDto createDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "location", source = "locationDto")
    void updateEntityFromDto(ProjectUpdateDto updateDto, @MappingTarget Project project);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "projectManager", ignore = true)
    @Mapping(target = "locationDto", source = "location")
    ProjectWithEmployeesDto toWithEmployeesDto(Project project);

    default ProjectWithEmployeesDto toWithEmployeesDto(
            Project project,
            List<EmployeeReadDto> employees,
            EmployeeReadDto projectManager) {

        ProjectWithEmployeesDto dto = toWithEmployeesDto(project);

        dto.setEmployees(Optional.ofNullable(employees).orElse(List.of()));
        dto.setProjectManager(projectManager);

        return dto;
    }


    @Mapping(target = "location", source = "location.name")
    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    ProjectMapPointDto toMapPointDto(Project project);

    List<ProjectMapPointDto> toMapPointDtoList(List<Project> projects);
}
