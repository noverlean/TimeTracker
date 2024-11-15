package noverlin.timetracker.services.impl;

import jakarta.transaction.Transactional;
import noverlin.timetracker.DTOs.SessionDto;
import noverlin.timetracker.DTOs.UserActivityDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.Session;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.entities.UserProject;
import noverlin.timetracker.exceptions.custom.*;
import noverlin.timetracker.mappers.SessionMapper;
import noverlin.timetracker.mappers.UserMapperImpl;
import noverlin.timetracker.repositories.ProjectRepository;
import noverlin.timetracker.repositories.SessionRepository;
import noverlin.timetracker.repositories.UserProjectRepository;
import noverlin.timetracker.repositories.UserRepository;
import noverlin.timetracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapperImpl userMapper;
    @Autowired
    private SessionMapper sessionMapper;

    @Override
    public Boolean startSessionByProjectIdAndUserEmail(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка наличия начатой и не оконченной сессии
        if (userProject.getSessions().stream().anyMatch((x) -> x.getDuration() == 0)) {
            throw new SessionAlreadyStartedException();
        }
        else if (userProject.getProject().isFinished()) {
            throw new ProjectAlreadyFinishedException();
        }

        Session session = new Session(userProject);
        userProject.getSessions().add(session);

        sessionRepository.save(session);
        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean finishSessionByProjectIdAndUserEmail(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка отсутствия начатой и не оконченной сессии
        if (userProject.getSessions().stream().noneMatch((x) -> x.getDuration() == 0)) {
            throw new SessionAlreadyEndedException();
        }

        Session session = sessionRepository.findOpenedByUserProject(email, projectId).get();
        finishSession(session);

        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }

    public void finishSession(Session session) {
        if (session.getDuration() != 0)
        {
            throw new SessionAlreadyEndedException();
        }

        Project project = session.getUserProject().getProject();
        Long duration = Duration.between(session.getStartedIn(), Instant.now()).toSeconds();
        session.setDuration(duration);
        project.setAllTimeSpent(getAllTimeSpent(project));

        sessionRepository.save(session);
        projectRepository.save(project);
    }

    public Long getAllTimeSpent(Project project) {
        Long summaryProjectDuration = 0L;
        for (UserProject up : project.getUserProjects()) {
            for (Session t : up.getSessions()) {
                summaryProjectDuration += t.getDuration();
            }
        }
        return summaryProjectDuration;
    }

    @Override
    public UserActivityDto getAllSessionTimeByUserEmail(Integer projectId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<SessionDto> sessionDtos = sessionRepository.findAllByUserEmailAndProjectId(email, projectId)
                .stream()
                .map(x -> sessionMapper.modelToDto(x))
                .toList();
        Long userActivityDuration = 0L;
        for (SessionDto dto : sessionDtos) {
            userActivityDuration += dto.getDuration();
        }

        return new UserActivityDto()
                .setUserDto(userMapper.modelToDto(user))
                .setSessions(sessionDtos)
                .setAllTimeSpent(userActivityDuration);
    }
}
