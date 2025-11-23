package com.campus.sys.config;

import com.campus.sys.auth.Role;
import com.campus.sys.user.User;
import com.campus.sys.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
public class DataInit {
  @Bean
  CommandLineRunner initUsers(UserRepository repo) {
    return args -> {
      if (repo.findByUsername("admin").isEmpty()) {
        User u = new User();
        u.setUsername("admin");
        u.setPasswordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
        u.setName("管理员");
        u.setRole(Role.ADMIN);
        repo.save(u);
      }
    };
  }
}