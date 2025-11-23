package com.campus.sys.activity;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
  private final ActivityRepository repo;
  public ActivityService(ActivityRepository repo) { this.repo = repo; }

  public Activity create(Activity a) { return repo.save(a); }
  public Activity update(Long id, Activity patch) {
    Activity a = repo.findById(id).orElseThrow();
    if (patch.getName() != null) a.setName(patch.getName());
    if (patch.getTitle() != null) a.setTitle(patch.getTitle());
    if (patch.getDescription() != null) a.setDescription(patch.getDescription());
    if (patch.getTerm() != null) a.setTerm(patch.getTerm());
    if (patch.getGrade() != null) a.setGrade(patch.getGrade());
    return repo.save(a);
  }
  public Activity publish(Long id) {
    Activity a = repo.findById(id).orElseThrow();
    a.setStatus(ActivityStatus.PUBLISHED);
    a.setPublishedAt(Instant.now());
    return repo.save(a);
  }
  public List<Activity> query(ActivityStatus status, String name) {
    if (status != null) return repo.findByStatusOrderByPublishedAtDesc(status);
    if (name != null && !name.isBlank()) return repo.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(name);
    return repo.findAll();
  }
}