package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.ProjectMapPointDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapPointMapper {

    @Mapping(target = "teamSize", expression = "java(project.getEmployeeIds() != null ? project.getEmployeeIds().size() : 0)")
    ProjectMapPointDto toDto(Project project);
}
