package noverlin.timetracker.services;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.entities.Role;
import noverlin.timetracker.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
