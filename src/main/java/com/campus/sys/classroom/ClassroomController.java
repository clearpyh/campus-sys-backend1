package com.campus.sys.classroom;

import java.util.List;
import org.springframework.web.bind.annotation.*;

class CreateClassroomRequest {
  private String name;
  private Long activityId;
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Long getActivityId() { return activityId; }
  public void setActivityId(Long activityId) { this.activityId = activityId; }
}
class UpdateClassroomRequest {
  private String name;
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
}
class Ids {
  private List<Long> ids;
  public List<Long> getIds() { return ids; }
  public void setIds(List<Long> ids) { this.ids = ids; }
}

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
  private final ClassroomService service;
  public ClassroomController(ClassroomService service) { this.service = service; }

  @PostMapping
  public Classroom create(@RequestBody CreateClassroomRequest req) { return service.create(req.getName(), req.getActivityId()); }
  @PutMapping("/{id}")
  public Classroom update(@PathVariable("id") Long id, @RequestBody UpdateClassroomRequest req) { return service.update(id, req.getName()); }
  @GetMapping("/{id}")
  public org.springframework.http.ResponseEntity<Classroom> get(@PathVariable("id") Long id) { return org.springframework.http.ResponseEntity.of(java.util.Optional.ofNullable(service.get(id))); }
  @PostMapping("/{id}/students")
  public Classroom addStudents(@PathVariable("id") Long id, @RequestBody Ids ids) { return service.addStudents(id, ids.getIds()); }
  @PostMapping("/{id}/teachers")
  public Classroom setTeachers(@PathVariable("id") Long id, @RequestBody Ids ids) { return service.setTeachers(id, ids.getIds()); }
}