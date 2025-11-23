package com.campus.sys.auth;

import com.campus.sys.user.User;
import com.campus.sys.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

class LoginRequest {
  private String username;
  private String password;
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
class LoginResponse {
  private String token;
  private Long userId;
  private String name;
  private String role;
  public LoginResponse(String token, Long userId, String name, String role) { this.token = token; this.userId = userId; this.name = name; this.role = role; }
  public String getToken() { return token; }
  public Long getUserId() { return userId; }
  public String getName() { return name; }
  public String getRole() { return role; }
}

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    User u = userRepository.findByUsername(req.getUsername()).orElse(null);
    if (u == null || !BCrypt.checkpw(req.getPassword(), u.getPasswordHash())) return ResponseEntity.status(401).build();
    Map<String, Object> claims = new HashMap<>();
    claims.put("uid", u.getId());
    claims.put("role", u.getRole().name());
    String token = jwtUtil.generate(u.getUsername(), claims);
    return ResponseEntity.ok(new LoginResponse(token, u.getId(), u.getName(), u.getRole().name()));
  }
}