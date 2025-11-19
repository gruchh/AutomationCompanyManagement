package com.automationcompany.employee.mapper;

import com.automationcompany.commondomain.dto.BankDetailsDto;
import com.automationcompany.employee.model.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {

    BankDetailsMapper INSTANCE = Mappers.getMapper(BankDetailsMapper.class);

    BankDetailsDto toDto(BankDetails bankDetails);
    BankDetails toEntity(BankDetailsDto bankDetailsDto);
}