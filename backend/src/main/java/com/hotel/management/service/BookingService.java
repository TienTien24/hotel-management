package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Booking createBooking(Booking booking) {
        // Validate dates
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new RuntimeException("Check-in date must be before check-out date");
        }

        // Check for overlap
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                booking.getRoom().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Room is already booked for this period");
        }

        // Calculate total price
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (nights == 0) nights = 1;
        
        Room room = roomRepository.findById(booking.getRoom().getId()).get();
        booking.setTotalPrice(room.getPrice() * nights);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus(BookingStatus.CONFIRMED);
        
        // Update room status
        Room room = booking.getRoom();
        room.setStatus(RoomStatus.BOOKED);
        roomRepository.save(room);
        
        return bookingRepository.save(booking);
    }

    public Booking checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setStatus(BookingStatus.CONFIRMED);
        
        Room room = booking.getRoom();
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);
        
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public List<Booking> getCustomerBookings(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
}
