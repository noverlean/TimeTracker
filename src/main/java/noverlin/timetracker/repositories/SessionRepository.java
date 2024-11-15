package noverlin.timetracker.repositories;

import noverlin.timetracker.entities.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE s.userProject.user.email = ?1 AND s.userProject.project.id = ?2 AND s.duration = 0")
    Optional<Session> findOpenedByUserProject(String email, Integer projectId);
    @Query("SELECT s FROM Session s WHERE s.userProject.project.id = ?1 AND s.duration = 0")
    List<Session> findAllStartedSessionsOnProject(Integer projectId);
    @Query("SELECT s FROM Session s WHERE s.userProject.user.email = ?1 AND s.userProject.project.id = ?2")
    List<Session> findAllByUserEmailAndProjectId(String email, Integer projectId);
}
