package com.hotel.management;

import com.hotel.management.enums.RoleName;
import com.hotel.management.model.User;
import com.hotel.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HotelManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(new User(null, "admin", passwordEncoder.encode("password"), "System Admin", "admin@hotel.com", "0123456789", RoleName.ADMIN));
            }
            if (!userRepository.existsByUsername("staff")) {
                userRepository.save(new User(null, "staff", passwordEncoder.encode("password"), "Hotel Staff", "staff@hotel.com", "0987654321", RoleName.STAFF));
            }
            if (!userRepository.existsByUsername("customer")) {
                userRepository.save(new User(null, "customer", passwordEncoder.encode("password"), "John Doe", "john@gmail.com", "0111222333", RoleName.CUSTOMER));
            }
        };
    }
}
