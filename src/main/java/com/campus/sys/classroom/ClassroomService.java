package com.campus.sys.classroom;

import com.campus.sys.activity.Activity;
import com.campus.sys.activity.ActivityRepository;
import com.campus.sys.activity.ActivityStatus;
import com.campus.sys.student.Student;
import com.campus.sys.student.StudentRepository;
import com.campus.sys.teacher.Teacher;
import com.campus.sys.teacher.TeacherRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
  private final ClassroomRepository repo;
  private final ActivityRepository activities;
  private final StudentRepository students;
  private final TeacherRepository teachers;
  public ClassroomService(ClassroomRepository repo, ActivityRepository activities, StudentRepository students, TeacherRepository teachers) {
    this.repo = repo; this.activities = activities; this.students = students; this.teachers = teachers;
  }

  public Classroom create(String name, Long activityId) {
    Activity a = activities.findById(activityId).orElseThrow();
    if (a.getStatus() != ActivityStatus.PUBLISHED) throw new IllegalStateException("activity not published");
    Classroom c = new Classroom();
    c.setName(name);
    c.setActivity(a);
    return repo.save(c);
  }
  public Classroom update(Long id, String name) {
    Classroom c = repo.findById(id).orElseThrow();
    if (name != null) c.setName(name);
    return repo.save(c);
  }
  public Classroom addStudents(Long id, List<Long> studentIds) {
    Classroom c = repo.findById(id).orElseThrow();
    List<Student> s = students.findAllById(studentIds);
    c.getStudents().addAll(s);
    return repo.save(c);
  }
  public Classroom setTeachers(Long id, List<Long> teacherIds) {
    Classroom c = repo.findById(id).orElseThrow();
    List<Teacher> t = teachers.findAllById(teacherIds);
    if (t.size() > 2) throw new IllegalArgumentException("max two teachers");
    c.setTeachers(t);
    return repo.save(c);
  }
  public Classroom get(Long id) {
    return repo.findById(id).orElseThrow();
  }
}