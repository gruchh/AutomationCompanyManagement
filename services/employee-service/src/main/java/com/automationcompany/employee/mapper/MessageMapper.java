package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.model.dto.SendMessageDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderName", expression = "java(getFullName(message.getSender()))")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "recipientName", expression = "java(getFullName(message.getRecipient()))")
    @Mapping(target = "content", source = "content")
    MessageDTO toDTO(Message message);

    default String getFullName(Employee employee) {
        if (employee == null) return "Unknown";
        return employee.getFirstName() + " " + employee.getLastName();
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "content", source = "content")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    Message toEntity(SendMessageDTO dto);
}