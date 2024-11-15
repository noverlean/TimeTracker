package noverlin.timetracker.services.impl;

import jakarta.transaction.Transactional;
import noverlin.timetracker.DTOs.TimingDto;
import noverlin.timetracker.DTOs.UserActivityDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.Timing;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.entities.UserProject;
import noverlin.timetracker.exceptions.custom.*;
import noverlin.timetracker.mappers.TimingMapper;
import noverlin.timetracker.mappers.UserMapperImpl;
import noverlin.timetracker.repositories.ProjectRepository;
import noverlin.timetracker.repositories.TimingRepository;
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
    private TimingRepository timingRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapperImpl userMapper;
    @Autowired
    private TimingMapper timingMapper;

    @Override
    public Boolean startSessionByProjectIdAndUserEmail(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка наличия начатой и не оконченной сессии
        if (userProject.getTimings().stream().anyMatch((x) -> x.getDuration() == 0)) {
            throw new SessionAlreadyStartedException();
        }
        else if (userProject.getProject().isFinished()) {
            throw new ProjectAlreadyFinishedException();
        }

        Timing timing = new Timing(userProject);
        userProject.getTimings().add(timing);

        timingRepository.save(timing);
        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean finishSessionByProjectIdAndUserEmail(Integer projectId, String email) {
        UserProject userProject = userProjectRepository.findByUserEmailAndProjectId(email, projectId)
                .orElseThrow(UserProjectNotFoundException::new);

        //проверка отсутствия начатой и не оконченной сессии
        if (userProject.getTimings().stream().noneMatch((x) -> x.getDuration() == 0)) {
            throw new SessionAlreadyEndedException();
        }

        Timing timing = timingRepository.findOpenedByUserProject(email, projectId).get();
        finishSession(timing);

        userProjectRepository.save(userProject);
        return Boolean.TRUE;
    }

    public void finishSession(Timing timing) {
        if (timing.getDuration() != 0)
        {
            throw new SessionAlreadyEndedException();
        }

        Project project = timing.getUserProject().getProject();
        Long duration = Duration.between(timing.getStartedIn(), Instant.now()).toSeconds();
        timing.setDuration(duration);
        project.setAllTimeSpent(getAllTimeSpent(project));

        timingRepository.save(timing);
        projectRepository.save(project);
    }

    public Long getAllTimeSpent(Project project) {
        Long summaryProjectDuration = 0L;
        for (UserProject up : project.getUserProjects()) {
            for (Timing t : up.getTimings()) {
                summaryProjectDuration += t.getDuration();
            }
        }
        return summaryProjectDuration;
    }

    @Override
    public UserActivityDto getAllSessionTimeByUserEmail(Integer projectId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<TimingDto> timingDtos = timingRepository.findAllByUserEmailAndProjectId(email, projectId)
                .stream()
                .map(x -> timingMapper.modelToDto(x))
                .toList();
        Long userActivityDuration = 0L;
        for (TimingDto dto : timingDtos) {
            userActivityDuration += dto.getDuration();
        }

        return new UserActivityDto()
                .setUserDto(userMapper.modelToDto(user))
                .setSessions(timingDtos)
                .setAllTimeSpent(userActivityDuration);
    }
}
