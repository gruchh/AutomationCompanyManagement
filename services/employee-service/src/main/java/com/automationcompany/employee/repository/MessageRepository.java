package com.automationcompany.employee.repository;

import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientAndIsDeletedFalse(Employee recipient);
    List<Message> findBySenderAndIsDeletedFalse(Employee sender);
    List<Message> findByRecipientAndIsReadFalseAndIsDeletedFalse(Employee recipient);
}