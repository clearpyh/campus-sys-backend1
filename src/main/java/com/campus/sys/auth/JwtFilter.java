package com.campus.sys.auth;

import com.campus.sys.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;
    if (path.startsWith("/auth/login")) return true;
    if (path.startsWith("/actuator")) return true;
    if (path.equals("/error")) return true;
    if (path.startsWith("/v3/api-docs")) return true;
    if (path.startsWith("/swagger-ui")) return true;
    return false;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      response.setStatus(401);
      return;
    }
    String token = auth.substring(7);
    io.jsonwebtoken.Claims claims;
    try {
      claims = jwtUtil.validate(token);
    } catch (Exception e) {
      response.setStatus(401);
      return;
    }
    
    filterChain.doFilter(request, response);
  }
}