package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.security.JwtRequest;
import noverlin.timetracker.DTOs.security.JwtResponse;

public interface AuthService {
    JwtResponse logIn(JwtRequest jwtRequest);
    JwtResponse signUp(JwtRequest jwtRequest);
}
