package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.DTOs.security.JwtRequest;
import noverlin.timetracker.DTOs.security.JwtResponse;
import noverlin.timetracker.entities.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDto projectDto);
    void linkUserToProject(Integer projectId, String email);
    Project getProject(Integer projectId);
    List<Project> getAllProjects();
}
