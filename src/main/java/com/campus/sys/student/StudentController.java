package com.campus.sys.student;

import java.util.List;
import org.springframework.web.bind.annotation.*;

class Ids {
  private List<Long> ids;
  public List<Long> getIds() { return ids; }
  public void setIds(List<Long> ids) { this.ids = ids; }
}

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final StudentService service;
  public StudentController(StudentService service) { this.service = service; }

  @PostMapping
  public Student add(@RequestBody Student s) { return service.add(s); }
  @PostMapping("/{id}/audit")
  public Student audit(@PathVariable("id") Long id) { return service.audit(id); }
  @PostMapping("/batchAudit")
  public List<Student> batchAudit(@RequestBody Ids ids) { return service.batchAudit(ids.getIds()); }
}