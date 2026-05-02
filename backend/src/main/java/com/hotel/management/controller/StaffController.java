package com.hotel.management.controller;

import com.hotel.management.dto.RegisterRequest;
import com.hotel.management.model.User;
import com.hotel.management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    AuthService authService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createStaff(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            authService.registerStaff(signUpRequest);
            return ResponseEntity.ok("Nhân viên đã được tạo thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllStaff() {
        List<User> allUsers = authService.getAllUsers();
        List<User> staffOnly = allUsers.stream()
                .filter(user -> user.getRole() == com.hotel.management.enums.RoleName.STAFF)
                .toList();
        return ResponseEntity.ok(staffOnly);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        try {
            User user = authService.getUserById(id);
            if (user.getRole() != com.hotel.management.enums.RoleName.STAFF) {
                return ResponseEntity.badRequest().body("Người dùng không phải là nhân viên");
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> lockStaff(@PathVariable Long id, @RequestParam boolean lock) {
        try {
            User user = authService.getUserById(id);
            if (user.getRole() != com.hotel.management.enums.RoleName.STAFF) {
                return ResponseEntity.badRequest().body("Chỉ có thể khóa/mở khóa tài khoản nhân viên");
            }
            authService.lockUser(id, lock);
            return ResponseEntity.ok(lock ? "Nhân viên đã bị khóa" : "Nhân viên đã được mở khóa");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        try {
            User user = authService.getUserById(id);
            if (user.getRole() != com.hotel.management.enums.RoleName.STAFF) {
                return ResponseEntity.badRequest().body("Chỉ có thể xóa tài khoản nhân viên");
            }
            authService.deleteUser(id);
            return ResponseEntity.ok("Nhân viên đã được xóa thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
