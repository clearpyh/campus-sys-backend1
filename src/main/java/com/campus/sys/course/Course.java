package com.campus.sys.course;

import com.campus.sys.activity.Activity;
import com.campus.sys.classroom.Classroom;
import com.campus.sys.teacher.Teacher;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @ManyToOne(optional = false)
  private Activity activity;
  @ManyToOne
  private Classroom classroom;
  @ManyToOne(optional = false)
  private Teacher teacher;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Activity getActivity() { return activity; }
  public void setActivity(Activity activity) { this.activity = activity; }
  public Classroom getClassroom() { return classroom; }
  public void setClassroom(Classroom classroom) { this.classroom = classroom; }
  public Teacher getTeacher() { return teacher; }
  public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}