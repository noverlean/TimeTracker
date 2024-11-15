package noverlin.timetracker.services;

import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.Role;
import noverlin.timetracker.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String username);
    User createNewUser(UserDto userDto);
    User getUser(String email);
    List<User> getAllUsers();
}
