package noverlin.timetracker.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import noverlin.timetracker.entities.UserProject;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserDto {
    private String name;
    private String email;
    private String password;
}
