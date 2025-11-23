package com.campus.sys.classroom;

import com.campus.sys.activity.Activity;
import com.campus.sys.student.Student;
import com.campus.sys.teacher.Teacher;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classrooms")
public class Classroom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @ManyToOne(optional = false)
  private Activity activity;
  @ManyToMany
  @JoinTable(name = "classroom_teachers")
  private List<Teacher> teachers = new ArrayList<>();
  @ManyToMany
  @JoinTable(name = "classroom_students")
  private List<Student> students = new ArrayList<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Activity getActivity() { return activity; }
  public void setActivity(Activity activity) { this.activity = activity; }
  public List<Teacher> getTeachers() { return teachers; }
  public void setTeachers(List<Teacher> teachers) { this.teachers = teachers; }
  public List<Student> getStudents() { return students; }
  public void setStudents(List<Student> students) { this.students = students; }
}