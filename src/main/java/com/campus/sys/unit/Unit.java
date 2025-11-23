package com.campus.sys.unit;

import com.campus.sys.course.Course;
import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private Course course;
  @Column(nullable = false)
  private String chapter;
  @Column(nullable = false)
  private String section;
  private String docUrl;
  private String videoUrl;
  @Column(nullable = false)
  private Integer points;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Course getCourse() { return course; }
  public void setCourse(Course course) { this.course = course; }
  public String getChapter() { return chapter; }
  public void setChapter(String chapter) { this.chapter = chapter; }
  public String getSection() { return section; }
  public void setSection(String section) { this.section = section; }
  public String getDocUrl() { return docUrl; }
  public void setDocUrl(String docUrl) { this.docUrl = docUrl; }
  public String getVideoUrl() { return videoUrl; }
  public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
  public Integer getPoints() { return points; }
  public void setPoints(Integer points) { this.points = points; }
}