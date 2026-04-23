package com.hotel.management.service;

import com.hotel.management.enums.RoomStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.Room;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        if (room.getStatus() == null) {
            room.setStatus(RoomStatus.AVAILABLE);
        }
        if (room.getCapacity() == null || room.getCapacity() <= 0) {
            room.setCapacity(defaultCapacity(room.getCategory()));
        }
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setCategory(roomDetails.getCategory());
        room.setType(roomDetails.getType());
        room.setCapacity(roomDetails.getCapacity() != null && roomDetails.getCapacity() > 0
                ? roomDetails.getCapacity()
                : defaultCapacity(roomDetails.getCategory()));
        room.setPrice(roomDetails.getPrice());
        room.setDescription(roomDetails.getDescription());
        room.setImageUrl(roomDetails.getImageUrl());
        room.setStatus(roomDetails.getStatus());
        return roomRepository.save(room);
    }

    public List<Room> searchRooms(LocalDate checkInDate, LocalDate checkOutDate, Integer numberOfGuests, String category) {
        return roomRepository.findAll().stream()
                .filter(room -> room.getStatus() != RoomStatus.MAINTENANCE)
                .filter(room -> category == null || category.isBlank() || "All".equalsIgnoreCase(category) || category.equalsIgnoreCase(room.getCategory()))
                .filter(room -> numberOfGuests == null || numberOfGuests <= 0 || (room.getCapacity() != null && room.getCapacity() >= numberOfGuests))
                .filter(room -> isAvailableForDateRange(room, checkInDate, checkOutDate))
                .collect(Collectors.toList());
    }

    private boolean isAvailableForDateRange(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            return room.getStatus() != RoomStatus.OCCUPIED;
        }

        List<Booking> overlapping = bookingRepository.findOverlappingBookings(room.getId(), checkInDate, checkOutDate);
        return overlapping.isEmpty();
    }

    private Integer defaultCapacity(String category) {
        if ("Suite".equalsIgnoreCase(category)) {
            return 4;
        }
        if ("Deluxe".equalsIgnoreCase(category)) {
            return 3;
        }
        return 2;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
