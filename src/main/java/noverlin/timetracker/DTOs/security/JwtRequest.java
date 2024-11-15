package unicore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtRequest {
    private String email;
    private String password;

    public JwtRequest(RegistrationCredentials registrationCredentials)
    {
        email = registrationCredentials.getEmail();
        password = registrationCredentials.getPassword();
    }

}
