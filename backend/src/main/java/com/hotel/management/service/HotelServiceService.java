package com.hotel.management.service;

import com.hotel.management.model.Booking;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.HotelService;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.BookingServiceUsageRepository;
import com.hotel.management.repository.HotelServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotelServiceService {
    @Autowired
    private HotelServiceRepository hotelServiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceUsageRepository bookingServiceUsageRepository;

    public List<HotelService> getAllServices() {
        return hotelServiceRepository.findAll();
    }

    public BookingServiceUsage addServiceToBooking(Long bookingId, Long serviceId, Integer quantity) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));
        
        HotelService service = hotelServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));

        BookingServiceUsage usage = new BookingServiceUsage();
        usage.setBooking(booking);
        usage.setService(service);
        usage.setQuantity(quantity);
        usage.setUsedDate(LocalDateTime.now());

        return bookingServiceUsageRepository.save(usage);
    }

    public List<BookingServiceUsage> getUsagesByBookingId(Long bookingId) {
        return bookingServiceUsageRepository.findByBookingId(bookingId);
    }

    public HotelService createService(HotelService hotelService) {
        return hotelServiceRepository.save(hotelService);
    }

    public HotelService updateService(Long id, HotelService serviceDetails) {
        HotelService service = hotelServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với id: " + id));
        service.setName(serviceDetails.getName());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        return hotelServiceRepository.save(service);
    }

    public void deleteService(Long id) {
        HotelService service = hotelServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với id: " + id));
        hotelServiceRepository.delete(service);
    }
}
