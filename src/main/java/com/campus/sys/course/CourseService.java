package com.campus.sys.course;

import com.campus.sys.activity.Activity;
import com.campus.sys.activity.ActivityRepository;
import com.campus.sys.activity.ActivityStatus;
import com.campus.sys.classroom.Classroom;
import com.campus.sys.classroom.ClassroomRepository;
import com.campus.sys.teacher.Teacher;
import com.campus.sys.teacher.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
  private final CourseRepository repo;
  private final ActivityRepository activities;
  private final ClassroomRepository classrooms;
  private final TeacherRepository teachers;
  public CourseService(CourseRepository repo, ActivityRepository activities, ClassroomRepository classrooms, TeacherRepository teachers) {
    this.repo = repo; this.activities = activities; this.classrooms = classrooms; this.teachers = teachers;
  }
  public Course create(String name, Long activityId, Long classroomId, Long teacherId) {
    Activity a = activities.findById(activityId).orElseThrow();
    if (a.getStatus() != ActivityStatus.PUBLISHED) throw new IllegalStateException("activity not published");
    Course c = new Course();
    c.setName(name);
    c.setActivity(a);
    Teacher t = teachers.findById(teacherId).orElseThrow();
    c.setTeacher(t);
    if (classroomId != null) {
      Classroom cls = classrooms.findById(classroomId).orElseThrow();
      c.setClassroom(cls);
    }
    return repo.save(c);
  }
}