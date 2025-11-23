package com.campus.sys.unit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
  List<Unit> findByCourseIdOrderByIdAsc(Long courseId);
}