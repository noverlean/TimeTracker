package noverlin.timetracker.DTOs.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
