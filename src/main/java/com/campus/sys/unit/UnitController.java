package com.campus.sys.unit;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/units")
public class UnitController {
  private final UnitService service;
  public UnitController(UnitService service) { this.service = service; }

  @PostMapping("/course/{courseId}")
  public Unit create(@PathVariable("courseId") Long courseId, @RequestBody Unit u) { return service.create(courseId, u); }
  @GetMapping("/course/{courseId}")
  public List<Unit> list(@PathVariable("courseId") Long courseId) { return service.list(courseId); }
}