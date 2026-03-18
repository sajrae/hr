package com.company.hr.common;

import com.company.hr.common.repository.SystemSettingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    private Map<String, String> settingsCache = new HashMap<>();

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    @PostConstruct
    public void loadSettings() {
        List<SystemSettings> settings = systemSettingRepository.findAll();

        settingsCache = settings.stream()
                .collect(Collectors.toMap(
                        SystemSettings::getSettingKey,
                        SystemSettings::getSettingValue
                ));
    }

    public String get(String key) {
        return settingsCache.get(key);
    }

    public String get(String key, String defaultValue) {
        return settingsCache.get(key);
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(settingsCache.get(key));
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return Boolean.parseBoolean(settingsCache.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(settingsCache.get(key));
    }

    public int getInt(String key, int defaultValue) {
        return Integer.parseInt(settingsCache.get(key));
    }

    public LocalTime getTime(String key) {
        return LocalTime.parse(settingsCache.get(key));
    }

    public LocalDate getDate(String key) {
        return LocalDate.parse(settingsCache.get(key));
    }

    public List<SystemSettings> findAll() {
        return systemSettingRepository.findAll();
    }

    public void save(SystemSettings systemSettings) {
        systemSettingRepository.save(systemSettings);
    }

    public void delete(SystemSettings systemSettings) {
        systemSettingRepository.delete(systemSettings);
    }
}
