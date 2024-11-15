package noverlin.timetracker.services.impl;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.DTOs.security.JwtRequest;
import noverlin.timetracker.DTOs.security.JwtResponse;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.services.AuthService;
import noverlin.timetracker.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse logIn(@RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @Override
    public JwtResponse signUp(@RequestBody JwtRequest jwtRequest) {
        Optional<User> user = userService.findByEmail(jwtRequest.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("Такой email же используется, проверьте вводимые данные.");
        }
        UserDto userDto = new UserDto()
                .setEmail(jwtRequest.getEmail())
                .setPassword(jwtRequest.getPassword());

        userService.createNewUser(userDto);
        return logIn(jwtRequest);
    }
}
