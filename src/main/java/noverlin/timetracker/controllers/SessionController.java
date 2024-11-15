package noverlin.timetracker.controllers;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "/")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/projects/{id}/sessions/start")
    public ResponseEntity<Boolean> startSessionByProjectIdAndUserEmail(@PathVariable("id") Integer projectId, Principal principal) {
        return ResponseEntity.ok(sessionService.startSessionByProjectIdAndUserEmail(projectId, principal.getName()));
    }

    @PatchMapping("/projects/{id}/sessions/finish")
    public ResponseEntity<Boolean> finishSessionByProjectIdAndUserEmail(@PathVariable("id") Integer projectId, Principal principal) {
        return ResponseEntity.ok(sessionService.finishSessionByProjectIdAndUserEmail(projectId, principal.getName()));
    }
}
