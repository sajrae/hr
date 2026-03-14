package com.company.hr.core.repository;

import com.company.hr.core.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {
}
