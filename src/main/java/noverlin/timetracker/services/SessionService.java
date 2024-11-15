package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.UserActivityDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.Timing;

public interface SessionService {
    Boolean startSessionByProjectIdAndUserEmail(Integer projectId, String email);
    Boolean finishSessionByProjectIdAndUserEmail(Integer projectId, String email);
    void finishSession(Timing timing);
    Long getAllTimeSpent(Project project);
    UserActivityDto getAllSessionTimeByUserEmail(Integer projectId, String email);
}
