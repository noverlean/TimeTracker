package noverlin.timetracker.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class ProjectDto {
    @NotNull
    private String title;
    private String description;
    private Boolean finished;
    private Instant createdIn;
    private Long allTimeSpent;
}
