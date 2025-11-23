package com.campus.sys.activity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
  List<Activity> findByStatusOrderByPublishedAtDesc(ActivityStatus status);
  List<Activity> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name);
}