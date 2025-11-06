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
    @Mapping(target = "senderName", expression = "java(getSenderName(message))")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "recipientName", expression = "java(getRecipientName(message))")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "isRead", source = "isRead")
    @Mapping(target = "sentAt", source = "sentAt")
    @Mapping(target = "readAt", source = "readAt")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "priority", source = "priority")
    MessageDTO toDTO(Message message);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "type", ignore = true) // Set in service based on sender
    @Mapping(target = "sender", ignore = true) // Set in service
    @Mapping(target = "recipient", ignore = true) // Set in service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentAt", ignore = true) // Auto-generated
    @Mapping(target = "readAt", ignore = true)
    Message toEntity(SendMessageDTO dto);

    default String getSenderName(Message message) {
        if (message == null) {
            return "Unknown";
        }
        if (message.getSender() == null) {
            return "System";
        }
        return getFullName(message.getSender());
    }

    default String getRecipientName(Message message) {
        if (message == null || message.getRecipient() == null) {
            return "Unknown";
        }
        return getFullName(message.getRecipient());
    }

    default String getFullName(Employee employee) {
        if (employee == null) {
            return "Unknown";
        }

        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        if (firstName == null && lastName == null) {
            return "Unknown";
        }
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }

        return firstName + " " + lastName;
    }}