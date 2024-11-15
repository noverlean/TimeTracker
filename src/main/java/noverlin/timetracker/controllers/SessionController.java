package noverlin.timetracker.controllers;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.mappers.ProjectMapper;
import noverlin.timetracker.mappers.UserMapper;
import noverlin.timetracker.mappers.UserMapperImpl;
import noverlin.timetracker.repositories.UserRepository;
import noverlin.timetracker.services.SessionService;
import noverlin.timetracker.services.impl.ProjectServiceImpl;
import noverlin.timetracker.services.impl.SessionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "/")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServiceImpl projectService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("id") Integer id) {
        Project project = projectService.getProject(id);
        return ResponseEntity.ok(projectMapper.modelToDto(project));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(
                projects.stream()
                        .map(projectMapper::modelToDto)
                        .toList()
        );
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        Project project = projectService.createProject(projectDto);
        return ResponseEntity.ok(projectMapper.modelToDto(project));
    }

    @GetMapping("/users/self/projects")
    public ResponseEntity<List<ProjectDto>> getAllProjectsOfCurrentUser(Principal principal) {
        List<Project> projects = projectService.getAllProjectsOfUser(principal.getName());
        return ResponseEntity.ok(
                projects.stream()
                        .map(projectMapper::modelToDto)
                        .toList()
        );
    }

    @GetMapping("/projects/{id}/users")
    public ResponseEntity<List<UserDto>> getAllUsersOnProject(@PathVariable("id") Integer projectId) {
        List<User> projects = projectService.getAllUsersOnProject(projectId);
        return ResponseEntity.ok(
                projects.stream()
                        .map(userMapper::modelToDto)
                        .toList()
        );
    }

    @PostMapping("/projects/{id}/sessions/start")
    public ResponseEntity<Boolean> startSessionForProject(@PathVariable("id") Integer projectId, Principal principal) {
        return ResponseEntity.ok(sessionService.startSessionForProject(projectId, principal.getName()));
    }

    @PostMapping("/projects/{id}/sessions/end")
    public ResponseEntity<Boolean> endSessionForProject(@PathVariable("id") Integer projectId, Principal principal) {
        return ResponseEntity.ok(sessionService.endSessionForProject(projectId, principal.getName()));
    }
}
