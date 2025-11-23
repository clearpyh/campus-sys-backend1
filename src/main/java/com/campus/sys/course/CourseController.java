package com.campus.sys.course;

import org.springframework.web.bind.annotation.*;

class CreateCourseRequest {
  private String name;
  private Long activityId;
  private Long classroomId;
  private Long teacherId;
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Long getActivityId() { return activityId; }
  public void setActivityId(Long activityId) { this.activityId = activityId; }
  public Long getClassroomId() { return classroomId; }
  public void setClassroomId(Long classroomId) { this.classroomId = classroomId; }
  public Long getTeacherId() { return teacherId; }
  public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  private final CourseService service;
  public CourseController(CourseService service) { this.service = service; }

  @PostMapping
  public Course create(@RequestBody CreateCourseRequest req) { return service.create(req.getName(), req.getActivityId(), req.getClassroomId(), req.getTeacherId()); }
}