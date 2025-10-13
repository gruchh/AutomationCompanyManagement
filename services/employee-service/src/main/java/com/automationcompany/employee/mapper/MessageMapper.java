package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.dto.MessageDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderName", expression = "java(message.getSender().getFirstName() + \" \" + message.getSender().getLastName())")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "recipientName", expression = "java(message.getRecipient().getFirstName() + \" \" + message.getRecipient().getLastName())")
    @Mapping(target = "content", source = "decryptedContent")
    MessageDTO toDTO(Message message, String decryptedContent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    @Mapping(target = "encryptedContent", ignore = true)
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    Message toEntity(MessageDTO dto);
}