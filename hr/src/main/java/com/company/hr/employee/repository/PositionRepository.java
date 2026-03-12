package com.company.hr.employee.repository;

import com.company.hr.employee.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {
}
