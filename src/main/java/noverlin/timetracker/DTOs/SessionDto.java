package noverlin.timetracker.DTOs;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class SessionDto {
    private Long duration;
    private Instant startedIn;
}
