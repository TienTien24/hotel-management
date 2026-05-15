package com.hotel.management.service;

import com.hotel.management.enums.BookingStatus;
import com.hotel.management.enums.ServiceStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.model.BookingServiceUsage;
import com.hotel.management.model.HotelService;
import com.hotel.management.repository.BookingRepository;
import com.hotel.management.repository.BookingServiceUsageRepository;
import com.hotel.management.repository.HotelServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HotelServiceService {
    @Autowired
    private HotelServiceRepository hotelServiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceUsageRepository bookingServiceUsageRepository;

    @Autowired
    @Lazy
    private InvoiceService invoiceService;

    public List<HotelService> getAllServices() {
        return hotelServiceRepository.findAll();
    }

    public List<BookingServiceUsage> getAllUsages() {
        return bookingServiceUsageRepository.findAll();
    }

    public BookingServiceUsage addServiceToBooking(Long bookingId, Long serviceId, Integer quantity, String note) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Chỉ có thể ghi nhận dịch vụ khi khách đang lưu trú (đã check-in)");
        }

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        HotelService service = hotelServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));

        BookingServiceUsage usage = new BookingServiceUsage();
        usage.setBooking(booking);
        usage.setService(service);
        usage.setQuantity(quantity);
        usage.setNote(note);
        usage.setUsedDate(LocalDateTime.now());
        usage.setStatus(ServiceStatus.PENDING);

        return bookingServiceUsageRepository.save(usage);
    }

    public BookingServiceUsage updateUsageStatus(Long usageId, ServiceStatus status) {
        BookingServiceUsage usage = bookingServiceUsageRepository.findById(usageId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu dịch vụ"));
        usage.setStatus(status);
        BookingServiceUsage saved = bookingServiceUsageRepository.save(usage);

        if (usage.getBooking() != null && usage.getBooking().getId() != null) {
            invoiceService.recalculateAndSyncInvoice(usage.getBooking().getId());
        }

        return saved;
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
