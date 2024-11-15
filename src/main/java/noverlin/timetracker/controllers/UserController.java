package noverlin.timetracker.controllers;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.entities.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "/")
@RequiredArgsConstructor
public class AdminController {
//    private final UserService userService;
//    private final UserMapper userMapper;
//    private final AuthService authService;
//
//    public ResponseEntity<ProjectDto> createProject(@RequestBody @Valid ProjectDto projectDto) {
//        return authService.createUser(userDto);
//    }

}
