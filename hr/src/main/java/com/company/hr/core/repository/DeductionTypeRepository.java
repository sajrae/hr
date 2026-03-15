package com.company.hr.core.repository;

import com.company.hr.core.entity.DeductionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Long> {
    Optional<DeductionType> findByName(String name);
}
