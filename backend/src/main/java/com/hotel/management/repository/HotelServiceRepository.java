package com.hotel.management.repository;

import com.hotel.management.model.HotelService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {
}
