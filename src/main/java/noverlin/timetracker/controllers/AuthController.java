package noverlin.timetracker.controllers;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.security.JwtRequest;
import noverlin.timetracker.DTOs.security.JwtResponse;
import noverlin.timetracker.services.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> logIn(@RequestBody JwtRequest jwtRequest) {
        JwtResponse response = authService.logIn(jwtRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signUp(@RequestBody JwtRequest jwtRequest) {
        JwtResponse response = authService.signUp(jwtRequest);
        return ResponseEntity.ok(response);
    }
}
