package com.automationcompany.employee.mapper;

import com.automationcompany.commondomain.dto.EmergencyContactDto;
import com.automationcompany.employee.model.EmergencyContact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmergencyContactMapper {

    EmergencyContactMapper INSTANCE = Mappers.getMapper(EmergencyContactMapper.class);

    EmergencyContactDto toDto(EmergencyContact emergencyContact);
    EmergencyContact toEntity(EmergencyContactDto emergencyContactDto);
}