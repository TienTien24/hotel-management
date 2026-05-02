package com.hotel.management.service;

import com.hotel.management.model.ContactMessage;
import com.hotel.management.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactMessageService {
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactMessage saveMessage(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public void updateStatus(Long id, ContactMessage.MessageStatus status) {
        contactMessageRepository.findById(id).ifPresent(message -> {
            message.setStatus(status);
            contactMessageRepository.save(message);
        });
    }

    public void replyMessage(Long id, String replyContent) {
        contactMessageRepository.findById(id).ifPresent(message -> {
            message.setReplyContent(replyContent);
            message.setRepliedAt(java.time.LocalDateTime.now());
            message.setStatus(ContactMessage.MessageStatus.REPLIED);
            contactMessageRepository.save(message);
        });
    }
}
