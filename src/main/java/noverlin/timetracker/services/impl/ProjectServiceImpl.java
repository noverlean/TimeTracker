package noverlin.timetracker.services.impl;

import jakarta.transaction.Transactional;
import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.Timing;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.entities.UserProject;
import noverlin.timetracker.exceptions.custom.*;
import noverlin.timetracker.repositories.ProjectRepository;
import noverlin.timetracker.repositories.TimingRepository;
import noverlin.timetracker.repositories.UserProjectRepository;
import noverlin.timetracker.repositories.UserRepository;
import noverlin.timetracker.services.ProjectService;
import noverlin.timetracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private TimingRepository timingRepository;
    @Autowired
    private SessionService sessionService;

    @Override
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
                .setAllTimeSpent(0L)
                .setUserProjects(new ArrayList<>());

        return projectRepository.save(project);
    }

    @Override
    public void linkUserToProject(Integer projectId, String email) {
        Optional<UserProject> userProjectOpt = userProjectRepository.findByUserEmailAndProjectId(email, projectId);
        if (userProjectOpt.isPresent()) {
            throw new UserProjectAlreadyExistsException();
        }

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (user.getRoles().stream().anyMatch(x -> x.getName().equals("ADMIN"))) {
            throw new UnavailableLinkProjectToAdminException();
        }
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        UserProject userProject = new UserProject()
                .setUser(user)
                .setProject(project)
                .setTimings(new ArrayList<>());

        userProjectRepository.save(userProject);
    }

    @Override
    public Project getProject(Integer projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        return projectOpt.orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public List<Project> getAllProjects() {
        return StreamSupport.stream(
                projectRepository
                        .findAll()
                        .spliterator()
                , false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> getAllProjectsOfUser(String email) {
        return userProjectRepository.findAllWithUser(email)
                .stream()
                .map(UserProject::getProject)
                .toList();
    }

    @Override
    public List<User> getAllUsersOnProject(Integer projectId) {
        return userProjectRepository.findAllWithProject(projectId)
                .stream()
                .map(UserProject::getUser)
                .toList();
    }

    @Override
    @Transactional
    public Project finishProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        if (project.isFinished()) {
            throw new ProjectAlreadyFinishedException();
        }

        //закрывает все неоконченные сессии
        List<Timing> startedTimings = timingRepository.findAllStartedTimingsOnProject(projectId);
        startedTimings.stream().peek(timing -> {
            sessionService.finishSession(timing);
        }).forEach(timingRepository::save);

        project.setFinished(true);
        return projectRepository.save(project);
    }

    @Override
    public Project resumeProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        if (!project.isFinished()) {
            throw new ProjectAlreadyResumedException();
        }
        project.setFinished(false);
        return projectRepository.save(project);
    }
}
