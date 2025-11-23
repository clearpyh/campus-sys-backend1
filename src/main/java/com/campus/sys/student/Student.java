package com.campus.sys.student;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  private String grade;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StudentStatus status = StudentStatus.NEW;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getGrade() { return grade; }
  public void setGrade(String grade) { this.grade = grade; }
  public StudentStatus getStatus() { return status; }
  public void setStatus(StudentStatus status) { this.status = status; }
}