package com.campus.sys.teacher;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
  private final TeacherService service;
  public TeacherController(TeacherService service) { this.service = service; }

  @PostMapping
  public Teacher add(@RequestBody Teacher t) { return service.add(t); }
  @GetMapping("/{id}")
  public Teacher get(@PathVariable("id") Long id) { return service.get(id); }
  @GetMapping
  public List<Teacher> list() { return service.query(); }
}