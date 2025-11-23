package com.campus.sys.user;

import com.campus.sys.auth.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

class AddUserRequest {
  private String username;
  private String password;
  private String name;
  private Role role;
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
}

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserRepository repo;
  public UserController(UserRepository repo) { this.repo = repo; }

  @PostMapping
  public User add(@RequestBody AddUserRequest req) {
    User u = new User();
    u.setUsername(req.getUsername());
    u.setPasswordHash(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));
    u.setName(req.getName());
    u.setRole(req.getRole());
    return repo.save(u);
  }
  @GetMapping("/{id}")
  public ResponseEntity<User> get(@PathVariable Long id) { return ResponseEntity.of(repo.findById(id)); }
  @GetMapping
  public List<User> list(@RequestParam Optional<String> name, @RequestParam Optional<String> username) {
    if (username.isPresent()) return repo.findByUsername(username.get()).map(List::of).orElse(List.of());
    if (name.isPresent()) return repo.findAll().stream().filter(x -> x.getName().contains(name.get())).toList();
    return repo.findAll();
  }
}