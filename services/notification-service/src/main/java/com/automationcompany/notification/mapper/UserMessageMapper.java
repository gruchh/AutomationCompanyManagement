package com.automationcompany.notification.mapper;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import com.automationcompany.notification.model.UserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface UserMessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "read", constant = "false")
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "sentAt", expression = "java(dto.getSentAt() != null ? dto.getSentAt() : LocalDateTime.now())")
    UserMessage toEntity(NotificationEventDto dto);
}