package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectTechnology;
import com.automationcompany.project.model.dto.ProjectCardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectCardMapper {

    @Mapping(source = "description", target = "shortDescription", qualifiedByName = "shortenDescription")
    @Mapping(source = "technologies", target = "technologies", qualifiedByName = "convertTechnologies")
    ProjectCardDto toDto(Project project);

    List<ProjectCardDto> toDtoList(List<Project> projects);

    @Named("shortenDescription")
    default String shortenDescription(String description) {
        if (description == null) return null;
        return description.length() <= 200 ? description : description.substring(0, 200) + "...";
    }

    @Named("convertTechnologies")
    default List<ProjectTechnology> convertTechnologies(Set<ProjectTechnology> technologies) {
        return technologies.stream().collect(Collectors.toList());
    }
}
