package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.dto.ProjectCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectCardMapper {
    @Mapping(target = "location", source = "location.name")
    @Mapping(target = "teamSize", expression = "java(project.getEmployeeIds() != null ? project.getEmployeeIds().size() : 0)")
    ProjectCardDto toDto(Project project);
}
