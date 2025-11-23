package com.campus.sys.student;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
  private final StudentRepository repo;
  public StudentService(StudentRepository repo) { this.repo = repo; }

  public Student add(Student s) { return repo.save(s); }
  public Student audit(Long id) {
    Student s = repo.findById(id).orElseThrow();
    s.setStatus(StudentStatus.REVIEWED);
    return repo.save(s);
  }
  public List<Student> batchAudit(List<Long> ids) {
    return ids.stream().map(this::audit).toList();
  }
}