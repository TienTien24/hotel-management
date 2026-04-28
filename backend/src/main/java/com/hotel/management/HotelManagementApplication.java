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
import com.hotel.management.model.Booking;
import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.PaymentMethod;
import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.model.HotelService;
import com.hotel.management.repository.HotelServiceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HotelManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoomRepository roomRepository, 
                                    BookingRepository bookingRepository, InvoiceRepository invoiceRepository,
                                    HotelServiceRepository hotelServiceRepository,
                                    PasswordEncoder passwordEncoder) {
        return args -> {
            if (hotelServiceRepository.count() == 0) {
                System.out.println("Đang tạo dữ liệu dịch vụ mẫu...");
                hotelServiceRepository.save(new HotelService(null, "Ăn sáng tại phòng", 15.0, "Dịch vụ phục vụ bữa sáng tận phòng"));
                hotelServiceRepository.save(new HotelService(null, "Giặt là", 5.0, "Dịch vụ giặt quần áo (tính theo bộ)"));
                hotelServiceRepository.save(new HotelService(null, "Massage & Spa", 50.0, "Dịch vụ massage thư giãn 60 phút"));
                hotelServiceRepository.save(new HotelService(null, "Minibar (Nước ngọt)", 2.0, "Nước ngọt trong tủ lạnh"));
                hotelServiceRepository.save(new HotelService(null, "Minibar (Bia)", 3.0, "Bia trong tủ lạnh"));
                hotelServiceRepository.save(new HotelService(null, "Thuê xe máy", 10.0, "Dịch vụ thuê xe máy theo ngày"));
                System.out.println("Đã tạo xong dữ liệu dịch vụ mẫu!");
            }
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(new User(null, "admin", passwordEncoder.encode("password"), "System Admin", "admin@hotel.com", "0123456789", RoleName.ADMIN, false));
            }
            if (!userRepository.existsByUsername("staff")) {
                userRepository.save(new User(null, "staff", passwordEncoder.encode("password"), "Hotel Staff", "staff@hotel.com", "0987654321", RoleName.STAFF, false));
            }
            if (!userRepository.existsByUsername("customer")) {
                userRepository.save(new User(null, "customer", passwordEncoder.encode("password"), "John Doe", "john@gmail.com", "0111222333", RoleName.CUSTOMER, false));
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
                    // Varying capacity and price for "small rooms" (Standard)
                    int capacity = (i % 3 == 0) ? 1 : 2;
                    double price = 40.0 + (i % 5) * 5.0;
                    roomRepository.save(new Room(null, roomNum, "Standard", type, capacity, price, standardDesc, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                
                // Generate 20 Deluxe rooms (Floors 3-4)
                String[] deluxeTypes = {"Double", "Twin", "Ocean View", "City View"};
                for (int i = 1; i <= 20; i++) {
                    int floor = (i <= 10) ? 3 : 4;
                    int roomInFloor = (i <= 10) ? i : i - 10;
                    String roomNum = floor + String.format("%02d", roomInFloor);
                    String type = deluxeTypes[(i - 1) % deluxeTypes.length];
                    // Varying capacity and price for Deluxe
                    int capacity = 2 + (i % 3); // 2, 3, 4
                    double price = 100.0 + (i % 10) * 10.0;
                    roomRepository.save(new Room(null, roomNum, "Deluxe", type, capacity, price, deluxeDesc, "https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                
                // Generate 20 Suite rooms (Floors 5-6)
                String[] suiteTypes = {"Junior", "Executive", "Family", "Presidential"};
                for (int i = 1; i <= 20; i++) {
                    int floor = (i <= 10) ? 5 : 6;
                    int roomInFloor = (i <= 10) ? i : i - 10;
                    String roomNum = floor + String.format("%02d", roomInFloor);
                    String type = suiteTypes[(i - 1) % suiteTypes.length];
                    // Varying capacity and price for Suite
                    int capacity = 4 + (i % 7); // 4 to 10
                    double price = 250.0 + (i % 5) * 50.0;
                    roomRepository.save(new Room(null, roomNum, "Suite", type, capacity, price, suiteDesc, "https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80", RoomStatus.AVAILABLE));
                }
                System.out.println("Đã tạo xong 60 phòng mẫu thành công!");
            } else {
                System.out.println("Dữ liệu đã có đủ 60 phòng, không cần tạo thêm.");
            }

            // Create sample bookings and reviews if none exist
            if (bookingRepository.count() == 0) {
                System.out.println("Đang tạo thêm dữ liệu booking và đánh giá mẫu...");
                User customer = userRepository.findByUsername("customer").orElseThrow();
                List<Room> allRooms = roomRepository.findAll();

                if (!allRooms.isEmpty()) {
                    // Booking 1: Completed with review
                    Room room1 = allRooms.get(0);
                    Booking booking1 = new Booking(null, customer, room1, "John Doe", "0111222333", "john@gmail.com", "123 Main St", 2, LocalDate.now().minusDays(10), LocalDate.now().minusDays(7), BookingStatus.COMPLETED, PaymentMethod.VNPAY, PaymentStatus.PAID, room1.getPrice() * 3, LocalDateTime.now().minusDays(11), LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(7), "Kỳ nghỉ tuyệt vời", 5, "Phòng rất đẹp và dịch vụ tuyệt vời!", LocalDateTime.now().minusDays(6));
                    bookingRepository.save(booking1);

                    // Booking 2: Completed with review
                    Room room2 = allRooms.get(1);
                    Booking booking2 = new Booking(null, customer, room2, "Jane Smith", "0444555666", "jane@gmail.com", "456 Oak Ave", 1, LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), BookingStatus.COMPLETED, PaymentMethod.MOMO, PaymentStatus.PAID, room2.getPrice() * 2, LocalDateTime.now().minusDays(6), LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(3), "Trải nghiệm tốt", 4, "Trải nghiệm tốt, nhưng hơi ồn ào một chút.", LocalDateTime.now().minusDays(2));
                    bookingRepository.save(booking2);

                    // Booking 3: Completed with review
                    Room room3 = allRooms.get(2);
                    Booking booking3 = new Booking(null, customer, room3, "Peter Jones", "0777888999", "peter@gmail.com", "789 Pine Ln", 3, LocalDate.now().minusDays(20), LocalDate.now().minusDays(18), BookingStatus.COMPLETED, PaymentMethod.COD, PaymentStatus.PAID, room3.getPrice() * 2, LocalDateTime.now().minusDays(21), LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(18), "Sang trọng và đẳng cấp", 5, "Khách sạn sang trọng, rất đáng tiền!", LocalDateTime.now().minusDays(17));
                    bookingRepository.save(booking3);

                    // Booking 4: Completed with review
                    Room room4 = allRooms.size() > 30 ? allRooms.get(30) : allRooms.get(0);
                    Booking booking4 = new Booking(null, customer, room4, "Lê Thị D", "0123456789", "lethid@gmail.com", "Hà Nội", 2, LocalDate.now().minusDays(12), LocalDate.now().minusDays(10), BookingStatus.COMPLETED, PaymentMethod.VNPAY, PaymentStatus.PAID, room4.getPrice() * 2, LocalDateTime.now().minusDays(13), LocalDateTime.now().minusDays(12), LocalDateTime.now().minusDays(10), "View biển cực đẹp", 5, "Phòng Deluxe có view biển tuyệt đẹp, ngắm bình minh ngay tại giường. Dịch vụ phòng rất nhanh chóng.", LocalDateTime.now().minusDays(9));
                    bookingRepository.save(booking4);

                    // Booking 5: Completed with review
                    Room room5 = allRooms.size() > 50 ? allRooms.get(50) : allRooms.get(0);
                    Booking booking5 = new Booking(null, customer, room5, "Phạm Văn E", "0987654321", "phamvane@gmail.com", "Đà Nẵng", 4, LocalDate.now().minusDays(25), LocalDate.now().minusDays(22), BookingStatus.COMPLETED, PaymentMethod.MOMO, PaymentStatus.PAID, room5.getPrice() * 3, LocalDateTime.now().minusDays(26), LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(22), "Kỳ nghỉ gia đình ấm áp", 5, "Chúng tôi đã có một kỳ nghỉ gia đình tuyệt vời tại phòng Suite. Không gian rộng rãi, các con rất thích hồ bơi vô cực của khách sạn.", LocalDateTime.now().minusDays(21));
                    bookingRepository.save(booking5);

                    System.out.println("Đã tạo xong dữ liệu booking và đánh giá mẫu!");
                }
            }
        };
    }
}
