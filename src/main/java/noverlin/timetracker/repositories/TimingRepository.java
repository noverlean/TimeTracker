package noverlin.timetracker.repositories;

import noverlin.timetracker.entities.Timing;
import noverlin.timetracker.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimingRepository extends CrudRepository<Timing, Long> {
    @Query("SELECT t FROM Timing t WHERE t.userProject.user.email = ?1 AND t.userProject.project.id = ?2 AND t.duration = 0")
    Optional<Timing> findOpenedByUserProject(String email, Integer projectId);
    @Query("SELECT t FROM Timing t WHERE t.userProject.project.id = ?1 AND t.duration = 0")
    List<Timing> findAllStartedTimingsOnProject(Integer projectId);
}
