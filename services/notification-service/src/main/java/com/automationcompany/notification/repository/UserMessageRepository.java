package com.automationcompany.notification.repository;

import com.automationcompany.notification.model.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends MongoRepository<UserMessage, String> {
    
    List<UserMessage> findByRecipientIdOrderBySentAtDesc(Long recipientId);
    
    long countByRecipientIdAndIsReadFalse(Long recipientId);
}