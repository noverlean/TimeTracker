package noverlin.timetracker.controllers;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.exceptions.custom.UserNotFoundException;
import noverlin.timetracker.mappers.ProjectMapper;
import noverlin.timetracker.mappers.UserMapper;
import noverlin.timetracker.services.ProjectService;
import noverlin.timetracker.services.impl.ProjectServiceImpl;
import noverlin.timetracker.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ProjectService projectService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(
                users.stream()
                .map(userMapper::modelToDto)
                .toList()
        );
    }

    @GetMapping("/users/email={email}")
    public ResponseEntity<UserDto> getUser(@PathVariable("email") String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(userMapper.modelToDto(user.get()));
    }

    @GetMapping("/users/self")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return getUser(principal.getName());
    }

    @PatchMapping("/projects/{projectId}/link/users/email={email}")
    public ResponseEntity<Boolean> linkUserToProject(@PathVariable("projectId") Integer projectId, @PathVariable("email") String email) {
        projectService.linkUserToProject(projectId, email);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
