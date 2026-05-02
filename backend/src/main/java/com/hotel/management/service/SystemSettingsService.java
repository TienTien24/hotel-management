package com.hotel.management.service;

import com.hotel.management.model.SystemSettings;
import com.hotel.management.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingsService {
    private static final Long DEFAULT_ID = 1L;

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    public SystemSettings getSettings() {
        return systemSettingsRepository.findById(DEFAULT_ID)
                .orElseGet(this::createDefaultSettings);
    }

    public SystemSettings updateSettings(SystemSettings payload) {
        SystemSettings settings = getSettings();
        settings.setHotelName(payload.getHotelName());
        settings.setAddress(payload.getAddress());
        settings.setPhone(payload.getPhone());
        settings.setEmail(payload.getEmail());
        settings.setLogoUrl(payload.getLogoUrl());
        return systemSettingsRepository.save(settings);
    }

    private SystemSettings createDefaultSettings() {
        SystemSettings settings = new SystemSettings();
        settings.setId(DEFAULT_ID);
        settings.setHotelName("Sunshine Hotel");
        settings.setAddress("123 Duong ABC, Quan 1, TP.HCM");
        settings.setPhone("0123 456 678");
        settings.setEmail("info@sunshinehotel.com");
        settings.setLogoUrl("");
        return systemSettingsRepository.save(settings);
    }
}
