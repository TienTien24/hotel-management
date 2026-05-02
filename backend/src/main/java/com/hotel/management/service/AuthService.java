package com.hotel.management.service;

import com.hotel.management.dto.JwtResponse;
import com.hotel.management.dto.LoginRequest;
import com.hotel.management.dto.RegisterRequest;
import com.hotel.management.enums.RoleName;
import com.hotel.management.model.User;
import com.hotel.management.repository.UserRepository;
import com.hotel.management.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        System.out.println("Attempting to authenticate user: " + loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin người dùng sau khi xác thực"));

            if (user.getIsLocked() != null && user.getIsLocked()) {
                throw new RuntimeException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ Admin.");
            }

            System.out.println("User authenticated successfully: " + loginRequest.getUsername());
            return new JwtResponse(jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole());
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            System.out.println("Authentication failed for user " + loginRequest.getUsername() + ": Sai mật khẩu");
            throw e;
        } catch (Exception e) {
            System.out.println("Authentication error for user " + loginRequest.getUsername() + ": " + e.getMessage());
            throw e;
        }
    }

    public void registerUser(RegisterRequest signUpRequest) {
        System.out.println("Processing registration for: " + signUpRequest.getUsername());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            System.out.println("Registration failed: Username " + signUpRequest.getUsername() + " already exists");
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            System.out.println("Registration failed: Email " + signUpRequest.getEmail() + " already in use");
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User(null,
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFullName(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                RoleName.CUSTOMER,
                false);

        userRepository.save(user);
        System.out.println("User registered successfully: " + signUpRequest.getUsername());
    }

    public void registerStaff(RegisterRequest signUpRequest) {
        System.out.println("Processing staff registration by Admin for: " + signUpRequest.getUsername());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            System.out.println("Staff registration failed: Username " + signUpRequest.getUsername() + " already exists");
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            System.out.println("Staff registration failed: Email " + signUpRequest.getEmail() + " already in use");
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User(null,
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFullName(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                RoleName.STAFF,
                false);

        userRepository.save(user);
        System.out.println("Staff registered successfully: " + signUpRequest.getUsername());
    }

    public User lockUser(Long userId, boolean lock) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (user.getRole() == com.hotel.management.enums.RoleName.ADMIN) {
            throw new RuntimeException("Không thể khóa tài khoản Admin");
        }

        user.setIsLocked(lock);
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (user.getRole() == com.hotel.management.enums.RoleName.ADMIN) {
            throw new RuntimeException("Không thể xóa tài khoản Admin");
        }

        userRepository.delete(user);
    }
}
