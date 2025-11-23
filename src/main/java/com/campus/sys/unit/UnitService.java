package com.campus.sys.unit;

import com.campus.sys.course.Course;
import com.campus.sys.course.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
  private final UnitRepository repo;
  private final CourseRepository courses;
  public UnitService(UnitRepository repo, CourseRepository courses) { this.repo = repo; this.courses = courses; }

  public Unit create(Long courseId, Unit u) {
    Course c = courses.findById(courseId).orElseThrow();
    u.setCourse(c);
    return repo.save(u);
  }
  public List<Unit> list(Long courseId) { return repo.findByCourseIdOrderByIdAsc(courseId); }
}