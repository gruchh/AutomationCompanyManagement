package com.automationcompany.project.mapper;

import com.automationcompany.project.model.Location;
import com.automationcompany.project.model.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LocationMapper {

    LocationDto toDto(Location location);

    Location toEntity(LocationDto dto);

    List<LocationDto> toDtoList(List<Location> locations);

    void updateEntityFromDto(LocationDto dto, @MappingTarget Location location);
}