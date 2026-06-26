package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.User;
import ru.elizarovds.kpautoservice.repository.UserRepository;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        // Пароль TestPass222222
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }


        String credentials = request.getEmail() + ":" + request.getPassword();
        String base64Auth = Base64.getEncoder().encodeToString(credentials.getBytes());

        return ResponseEntity.ok(Map.of(
                "authorization", "Basic " + base64Auth,
                "role", user.getRole().name()
        ));
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public void setEmail(String email) { this.email = email; }
    }
}