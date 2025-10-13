package com.automationcompany.employee.service;

import com.automationcompany.employee.exception.EmployeeNotFoundException;
import com.automationcompany.employee.exception.MessageNotFoundException;
import com.automationcompany.employee.mapper.MessageMapper;
import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.EmployeeKeys;
import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.repository.EmployeeKeysRepository;
import com.automationcompany.employee.repository.EmployeeRepository;
import com.automationcompany.employee.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeKeysRepository employeeKeysRepository;
    private final RSAEncryptionService rsaService;
    private final MessageMapper messageMapper;

    @Transactional
    public MessageDTO sendMessage(Long senderId, Long recipientId, String subject, String content) {
        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new EmployeeNotFoundException("Nadawca nie został znaleziony"));

        Employee recipient = employeeRepository.findById(recipientId)
                .orElseThrow(() -> new EmployeeNotFoundException("Odbiorca nie został znaleziony"));

        EmployeeKeys recipientKeys = employeeKeysRepository.findByEmployee(recipient)
                .orElseThrow(() -> new IllegalStateException("Klucze odbiorcy nie zostały znalezione"));

        PublicKey recipientPublicKey = rsaService.stringToPublicKey(recipientKeys.getPublicKey());
        String encryptedContent = rsaService.encrypt(content, recipientPublicKey);

        Message message = Message.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(subject)
                .encryptedContent(encryptedContent)
                .isRead(false)
                .isDeleted(false)
                .sentAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        return messageMapper.toDTO(message, content);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getReceivedMessages(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Pracownik nie został znaleziony"));

        EmployeeKeys employeeKeys = employeeKeysRepository.findByEmployee(employee)
                .orElseThrow(() -> new IllegalStateException("Klucze pracownika nie zostały znalezione"));

        List<Message> messages = messageRepository.findByRecipientAndIsDeletedFalse(employee);
        PrivateKey privateKey = rsaService.stringToPrivateKey(employeeKeys.getEncryptedPrivateKey());

        return messages.stream()
                .map(msg -> messageMapper.toDTO(
                        msg,
                        rsaService.decrypt(msg.getEncryptedContent(), privateKey)
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getSentMessages(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Pracownik nie został znaleziony"));

        List<Message> messages = messageRepository.findBySenderAndIsDeletedFalse(employee);
        return messages.stream()
                .map(msg -> messageMapper.toDTO(msg, "[Zaszyfrowana treść]"))
                .collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long messageId, Long employeeId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Wiadomość nie została znaleziona"));

        if (!message.getRecipient().getId().equals(employeeId)) {
            throw new IllegalArgumentException("Brak uprawnień do oznaczenia tej wiadomości");
        }

        message.setIsRead(true);
        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Long messageId, Long employeeId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Wiadomość nie została znaleziona"));

        if (!message.getRecipient().getId().equals(employeeId)
                && !message.getSender().getId().equals(employeeId)) {
            throw new IllegalArgumentException("Brak uprawnień do usunięcia tej wiadomości");
        }

        message.setIsDeleted(true);
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public boolean isMessageRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Wiadomość nie została znaleziona"));
        return message.getIsRead();
    }
}
