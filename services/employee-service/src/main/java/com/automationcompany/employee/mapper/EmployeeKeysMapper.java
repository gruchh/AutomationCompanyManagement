package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.dto.EmployeeKeysDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeKeysMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", expression = "java(employeeKeys.getEmployee().getFirstName() + \" \" + employeeKeys.getEmployee().getLastName())")
    @Mapping(target = "publicKey", source = "publicKey")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "isActive", source = "isActive")
    EmployeeKeysDTO toDTO(EmployeeKeys employeeKeys);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "encryptedPrivateKey", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    EmployeeKeys toEntity(EmployeeKeysDTO dto);
}