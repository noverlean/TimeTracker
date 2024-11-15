package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.DTOs.security.JwtRequest;
import noverlin.timetracker.DTOs.security.JwtResponse;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDto projectDto);
    void linkUserToProject(Integer projectId, String email);
    Project getProject(Integer projectId);
    List<Project> getAllProjects();
    List<Project> getAllProjectsOfUser(String name);
    List<User> getAllUsersOnProject(Integer projectId);
    Project finishProject(Integer projectId);
    Project resumeProject(Integer id);
}
