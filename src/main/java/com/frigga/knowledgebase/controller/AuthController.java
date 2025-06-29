package com.frigga.knowledgebase.controller;
import com.frigga.knowledgebase.dto.*;
import com.frigga.knowledgebase.model.User;
import com.frigga.knowledgebase.repository.UserRepository;
import com.frigga.knowledgebase.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
         System.out.println("User Found: " + userOpt.isPresent());

    if (userOpt.isPresent()) {
        String dbPassword = userOpt.get().getPassword();
        System.out.println("Password from DB: " + dbPassword);

        boolean matches = passwordEncoder.matches(request.getPassword(), dbPassword);
        System.out.println("Password Match Result: " + matches);

        if (matches) {
            String token = jwtUtil.generateToken(userOpt.get().getId());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            System.out.println("Password Mismatch.");
        }
    } else {
        System.out.println("User Not Found in DB.");
    }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}

