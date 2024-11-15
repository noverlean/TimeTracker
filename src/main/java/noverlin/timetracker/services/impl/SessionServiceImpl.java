package noverlin.timetracker.services.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
                .setAllTimeSpent(0)
                .setUserProjects(new ArrayList<>());

        return projectRepository.save(project);
    }

    @Override
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
    public Boolean startSessionForProject(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка наличия начатой и не оконченной сессии
        if (userProject.getTimings().stream().anyMatch((x) -> x.getDuration() == 0))
        {
            throw new SessionAlreadyStartedException();
        }

        Timing timing = new Timing(userProject);
        userProject.getTimings().add(timing);

        timingRepository.save(timing);
        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }

    @Override
    public Boolean endSessionForProject(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка наличия не закрытой сессии в orElseThrow
        Timing timing = timingRepository.findOpenedByUserProject(email, projectId)
                .orElseThrow(SessionAlreadyEndedException::new);
        Long duration = Duration.between(Instant.now(), timing.getStartedIn()).toMillis();
        timing.setDuration(duration);

        timingRepository.save(timing);
        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }
}
