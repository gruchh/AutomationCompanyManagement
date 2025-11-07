package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.dto.EmployeeCreateDto;
import com.automationcompany.employee.model.dto.EmployeeReadDto;
import com.automationcompany.employee.model.dto.EmployeeUpdateDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "terminationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Employee toEntity(EmployeeCreateDto dto);

    EmployeeReadDto toReadDTO(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pesel", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEmployeeFromDto(EmployeeUpdateDto dto, @MappingTarget Employee employee);
}