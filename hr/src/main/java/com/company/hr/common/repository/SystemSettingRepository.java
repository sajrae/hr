package com.company.hr.common.repository;

import com.company.hr.common.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSettings,Long> {

    Optional<SystemSettings> findBySettingKey(String settingKey);
}
