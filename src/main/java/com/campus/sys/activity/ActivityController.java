package com.campus.sys.activity;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
  private final ActivityService service;
  public ActivityController(ActivityService service) { this.service = service; }

  @PostMapping
  public Activity create(@RequestBody Activity a) { return service.create(a); }
  @PutMapping("/{id}")
  public Activity update(@PathVariable("id") Long id, @RequestBody Activity patch) { return service.update(id, patch); }
  @PostMapping("/{id}/publish")
  public Activity publish(@PathVariable("id") Long id) { return service.publish(id); }
  @GetMapping
  public List<Activity> query(@RequestParam(required = false) ActivityStatus status, @RequestParam(required = false) String name) { return service.query(status, name); }
  @GetMapping("/{id}")
  public ResponseEntity<Activity> get(@PathVariable("id") Long id) { return ResponseEntity.of(service.query(null, null).stream().filter(x -> x.getId().equals(id)).findFirst()); }
}