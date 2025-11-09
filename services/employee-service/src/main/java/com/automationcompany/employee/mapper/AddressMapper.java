package com.automationcompany.employee.mapper;

import com.automationcompany.commondomain.dto.AddressDto;
import com.automationcompany.employee.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}