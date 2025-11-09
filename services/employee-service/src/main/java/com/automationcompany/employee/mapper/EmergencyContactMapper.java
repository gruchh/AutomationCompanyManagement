package com.automationcompany.employee.mapper;

import com.automationcompany.commondomain.dto.EmergencyContactDto;
import com.automationcompany.employee.model.EmergencyContact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmergencyContactMapper {
    EmergencyContactDto toDto(EmergencyContact emergencyContact);
    EmergencyContact toEntity(EmergencyContactDto emergencyContactDto);
}