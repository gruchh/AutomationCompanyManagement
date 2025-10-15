package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.dto.EmployeeCreateDTO;
import com.automationcompany.employee.model.dto.EmployeeReadDTO;
import com.automationcompany.employee.model.dto.EmployeeUpdateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    Employee toEntity(EmployeeCreateDTO dto);
    EmployeeReadDTO toReadDTO(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeUpdateDTO dto, @MappingTarget Employee employee);
}
