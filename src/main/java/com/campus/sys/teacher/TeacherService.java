package com.campus.sys.teacher;

import com.campus.sys.auth.Role;
import com.campus.sys.user.User;
import com.campus.sys.user.UserRepository;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
  private final TeacherRepository repo;
  private final UserRepository users;
  public TeacherService(TeacherRepository repo, UserRepository users) { this.repo = repo; this.users = users; }

  public Teacher add(Teacher t) {
    Teacher saved = repo.save(t);
    User u = new User();
    u.setUsername("t" + saved.getId());
    u.setPasswordHash(BCrypt.hashpw("123456", BCrypt.gensalt()));
    u.setName(saved.getName());
    u.setRole(Role.TEACHER);
    users.save(u);
    return saved;
  }
  public Teacher get(Long id) { return repo.findById(id).orElseThrow(); }
  public List<Teacher> query() { return repo.findAll(); }
}