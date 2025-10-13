package com.automationcompany.employee.mapper;

import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.dto.MessageDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "id", source = "message.id")
    @Mapping(target = "senderId", source = "message.sender.id")
    @Mapping(target = "senderName",
            expression = "java(message.getSender().getFirstName() + \" \" + message.getSender().getLastName())")
    @Mapping(target = "recipientId", source = "message.recipient.id")
    @Mapping(target = "recipientName",
            expression = "java(message.getRecipient().getFirstName() + \" \" + message.getRecipient().getLastName())")
    @Mapping(target = "subject", source = "message.subject")
    @Mapping(target = "content", source = "decryptedContent")
    @Mapping(target = "isRead", source = "message.isRead")
    @Mapping(target = "sentAt", source = "message.sentAt")
    @Mapping(target = "readAt", source = "message.readAt")
    MessageDTO toDTO(Message message, String decryptedContent);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "isDeleted", constant = "false")
    Message toEntity(MessageDTO dto);
}