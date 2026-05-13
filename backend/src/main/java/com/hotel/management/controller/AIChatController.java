package com.hotel.management.controller;

import com.hotel.management.service.AIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message is required"));
        }
        
        String response = aiChatService.getAIResponse(message);
        return ResponseEntity.ok(Map.of("response", response));
    }
}
