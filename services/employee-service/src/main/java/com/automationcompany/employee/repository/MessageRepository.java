package com.automationcompany.employee.repository;

import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.MessageCategory;
import com.automationcompany.employee.model.MessagePriority;
import com.automationcompany.employee.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRecipientIdAndIsDeletedFalseOrderBySentAtDesc(Long recipientId);
    List<Message> findBySenderIdAndIsDeletedFalseOrderBySentAtDesc(Long senderId);
    Optional<Message> findByIdAndRecipientId(Long id, Long recipientId);
    Optional<Message> findByIdAndSenderId(Long id, Long senderId);
    List<Message> findByRecipientIdAndIsReadFalseAndIsDeletedFalseOrderBySentAtDesc(Long recipientId);
    long countByRecipientIdAndIsReadFalseAndIsDeletedFalse(Long recipientId);
    List<Message> findByRecipientIdAndCategoryAndIsDeletedFalseOrderBySentAtDesc(
            Long recipientId, MessageCategory category);
    List<Message> findByRecipientIdAndTypeAndIsDeletedFalseOrderBySentAtDesc(
            Long recipientId, MessageType type);
    List<Message> findByRecipientIdAndPriorityAndIsDeletedFalseOrderBySentAtDesc(
            Long recipientId, MessagePriority priority);
}