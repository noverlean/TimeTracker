package noverlin.timetracker.repositories;

import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.UserProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends CrudRepository<UserProject, Long> {
    @Query("SELECT up FROM UserProject up WHERE up.user.email = ?1 AND up.project.id = ?2")
    Optional<UserProject> findByUserEmailAndProjectId(String email, Integer projectId);
    @Query("SELECT up FROM UserProject up WHERE up.user.email = ?1")
    List<UserProject> findAllWithUser(String email);
    @Query("SELECT up FROM UserProject up WHERE up.project.id = ?1")
    List<UserProject> findAllWithProject(Integer projectId);
}
