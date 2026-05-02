package com.hotel.management.controller;

import com.hotel.management.dto.AdminCustomerSummary;
import com.hotel.management.service.AdminCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
@CrossOrigin(origins = "*")
public class AdminCustomerController {
    @Autowired
    private AdminCustomerService adminCustomerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminCustomerSummary>> getCustomers() {
        return ResponseEntity.ok(adminCustomerService.getCustomerSummaries());
    }
}
