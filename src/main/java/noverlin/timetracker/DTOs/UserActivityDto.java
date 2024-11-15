package noverlin.timetracker.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import noverlin.timetracker.entities.Session;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserActivityDto {
    private UserDto userDto;
    private List<SessionDto> sessions;
    private Long allTimeSpent;
}
