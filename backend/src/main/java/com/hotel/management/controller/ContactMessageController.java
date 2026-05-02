package com.hotel.management.controller;

import com.hotel.management.model.ContactMessage;
import com.hotel.management.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {
    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitMessage(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(contactMessageService.saveMessage(message));
    }

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    @PutMapping("/messages/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam ContactMessage.MessageStatus status) {
        contactMessageService.updateStatus(id, status);
        return ResponseEntity.ok("Status updated successfully");
    }

    @PostMapping("/messages/{id}/reply")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> replyMessage(@PathVariable Long id, @RequestBody java.util.Map<String, String> payload) {
        String replyContent = payload.get("replyContent");
        contactMessageService.replyMessage(id, replyContent);
        return ResponseEntity.ok("Replied successfully");
    }
}
