package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Message between employees")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique message identifier")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @Schema(description = "Message sender")
    @ToString.Exclude
    private Employee sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    @Schema(description = "Message recipient")
    @ToString.Exclude
    private Employee recipient;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Message content", example = "Hi, please approve my leave request.")
    private String content;

    @Column(nullable = false, length = 100)
    @Schema(description = "Message subject", example = "Leave Request")
    private String subject;

    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Whether the message has been read", defaultValue = "false")
    private Boolean isRead = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Date and time the message was sent")
    private LocalDateTime sentAt;

    @Schema(description = "Date and time the message was read (null if unread)")
    private LocalDateTime readAt;

    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Soft delete flag", defaultValue = "false")
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Message category", implementation = MessageCategory.class, defaultValue = "GENERAL")
    private MessageCategory category = MessageCategory.GENERAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Message priority", implementation = MessagePriority.class, defaultValue = "MEDIUM")
    private MessagePriority priority = MessagePriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Message type", implementation = MessageType.class, defaultValue = "USER")
    private MessageType type = MessageType.USER;
}