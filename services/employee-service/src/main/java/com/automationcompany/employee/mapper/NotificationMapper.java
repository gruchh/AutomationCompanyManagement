package com.automationcompany.employee.mapper;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.dto.SendMessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface NotificationMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "recipientEmail", source = "recipient.email")
    @Mapping(target = "sentAt", expression = "java(LocalDateTime.now())")
    NotificationEventDto toNotificationEvent(SendMessageDto request, Employee sender, Employee recipient);
}