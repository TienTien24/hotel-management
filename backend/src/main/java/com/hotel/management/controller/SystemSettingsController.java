package com.hotel.management.controller;

import com.hotel.management.model.SystemSettings;
import com.hotel.management.service.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/settings")
@CrossOrigin(origins = "*")
public class SystemSettingsController {
    @Autowired
    private SystemSettingsService systemSettingsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemSettings> getSettings() {
        return ResponseEntity.ok(systemSettingsService.getSettings());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemSettings> updateSettings(@RequestBody SystemSettings settings) {
        return ResponseEntity.ok(systemSettingsService.updateSettings(settings));
    }
}
