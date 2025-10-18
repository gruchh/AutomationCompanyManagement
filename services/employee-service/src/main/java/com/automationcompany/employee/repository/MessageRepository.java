package com.automationcompany.employee.repository;

import com.automationcompany.employee.model.Message;
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
}