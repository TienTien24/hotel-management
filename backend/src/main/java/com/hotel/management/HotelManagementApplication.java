package com.hotel.management;

import com.hotel.management.enums.RoleName;
import com.hotel.management.model.User;
import com.hotel.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Room;
import com.hotel.management.repository.RoomRepository;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.InvoiceRepository;

@SpringBootApplication
public class HotelManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoomRepository roomRepository, 
                                    BookingRepository bookingRepository, InvoiceRepository invoiceRepository,
                                    PasswordEncoder passwordEncoder) {
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

            if (roomRepository.count() != 60) {
                System.out.println("Đang tạo lại dữ liệu mẫu 60 phòng...");
                invoiceRepository.deleteAll();
                bookingRepository.deleteAll();
                roomRepository.deleteAll();
                
                String standardDesc = "Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.";
                String deluxeDesc = "Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.";
                String suiteDesc = "Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một ngôi nhà thứ hai đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.";

                // Generate 20 Standard rooms (Floors 1-2)
                String[] standardTypes = {"Single", "Double", "Twin"};
                for (int i = 1; i <= 20; i++) {
                    int floor = (i <= 10) ? 1 : 2;
                    int roomInFloor = (i <= 10) ? i : i - 10;
                    String roomNum = floor + String.format("%02d", roomInFloor);
                    String type = standardTypes[(i - 1) % standardTypes.length];
                    roomRepository.save(new Room(null, roomNum, "Standard", type, 2, 50.0, standardDesc, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                
                // Generate 20 Deluxe rooms (Floors 3-4)
                String[] deluxeTypes = {"Double", "Twin", "Ocean View", "City View"};
                for (int i = 1; i <= 20; i++) {
                    int floor = (i <= 10) ? 3 : 4;
                    int roomInFloor = (i <= 10) ? i : i - 10;
                    String roomNum = floor + String.format("%02d", roomInFloor);
                    String type = deluxeTypes[(i - 1) % deluxeTypes.length];
                    roomRepository.save(new Room(null, roomNum, "Deluxe", type, 3, 150.0, deluxeDesc, "https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                
                // Generate 20 Suite rooms (Floors 5-6)
                String[] suiteTypes = {"Junior", "Executive", "Family", "Presidential"};
                for (int i = 1; i <= 20; i++) {
                    int floor = (i <= 10) ? 5 : 6;
                    int roomInFloor = (i <= 10) ? i : i - 10;
                    String roomNum = floor + String.format("%02d", roomInFloor);
                    String type = suiteTypes[(i - 1) % suiteTypes.length];
                    roomRepository.save(new Room(null, roomNum, "Suite", type, 4, 300.0, suiteDesc, "https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                System.out.println("Đã tạo xong 60 phòng mẫu thành công!");
            } else {
                System.out.println("Dữ liệu đã có đủ 60 phòng, không cần tạo thêm.");
            }
        };
    }
}
