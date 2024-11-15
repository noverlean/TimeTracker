package noverlin.timetracker.DTOs.security;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtRequest {
    private String email;
    private String password;
}
