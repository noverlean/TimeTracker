package noverlin.timetracker.repositories;

import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    Optional<Project> findByTitle(String name);
}
