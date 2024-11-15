package noverlin.timetracker.services.impl;

import lombok.RequiredArgsConstructor;
import noverlin.timetracker.entities.Role;
import noverlin.timetracker.repositories.RoleRepository;
import noverlin.timetracker.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
