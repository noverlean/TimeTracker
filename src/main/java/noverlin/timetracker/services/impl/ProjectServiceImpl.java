package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.entities.UserProject;
import noverlin.timetracker.exceptions.custom.ProjectAlreadyExistsException;
import noverlin.timetracker.exceptions.custom.ProjectNotFoundException;
import noverlin.timetracker.exceptions.custom.UserNotFoundException;
import noverlin.timetracker.exceptions.custom.UserProjectNotFoundException;
import noverlin.timetracker.repositories.ProjectRepository;
import noverlin.timetracker.repositories.UserProjectRepository;
import noverlin.timetracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProjectRepository userProjectRepository;

    public Project createProject(ProjectDto projectDto) {
        Optional<Project> projectOpt = projectRepository.findByTitle(projectDto.getTitle());
        if (projectOpt.isPresent()) {
            throw new ProjectAlreadyExistsException();
        }

        Project project = new Project()
                .setTitle(projectDto.getTitle())
                .setDescription(projectDto.getDescription())
                .setFinished(false)
                .setCreatedIn(Instant.now())
                .setAllTimeSpent(0)
                .setUserProjects(new ArrayList<>());

        return projectRepository.save(project);
    }

    public void linkUserToProject(Integer projectId, String email) {
        Optional<UserProject> userProjectOpt = userProjectRepository.findByUserEmailAndProjectId(email, projectId);
        if (userProjectOpt.isPresent()) {
            throw new UserProjectNotFoundException();
        }

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        UserProject userProject = new UserProject()
                .setUser(user)
                .setProject(project)
                .setTimings(new ArrayList<>());

        userProjectRepository.save(userProject);
    }

    public Project getProject(Integer projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        return projectOpt.orElseThrow(ProjectNotFoundException::new);
    }
}
