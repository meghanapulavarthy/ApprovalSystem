package com.xalts.approvalsystem.controller;

import com.xalts.approvalsystem.dto.SignupRequest;
import com.xalts.approvalsystem.model.User;
import com.xalts.approvalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.map(value -> ResponseEntity.ok("User ID: " + value.getId()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }
}
