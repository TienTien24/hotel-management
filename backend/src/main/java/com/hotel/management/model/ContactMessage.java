package com.hotel.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private String address;

    @Column(columnDefinition = "TEXT")
    private String note;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.PENDING;

    public enum MessageStatus {
        PENDING, READ, REPLIED
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
