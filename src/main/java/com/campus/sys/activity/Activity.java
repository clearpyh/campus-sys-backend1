package com.campus.sys.activity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "activities")
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  private String title;
  @Column(length = 2000)
  private String description;
  private String term;
  private String grade;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ActivityStatus status = ActivityStatus.DRAFT;
  @Column(nullable = false)
  private Instant createdAt = Instant.now();
  private Instant publishedAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getTerm() { return term; }
  public void setTerm(String term) { this.term = term; }
  public String getGrade() { return grade; }
  public void setGrade(String grade) { this.grade = grade; }
  public ActivityStatus getStatus() { return status; }
  public void setStatus(ActivityStatus status) { this.status = status; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
  public Instant getPublishedAt() { return publishedAt; }
  public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
}