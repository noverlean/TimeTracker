package noverlin.timetracker.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(name = "timings")
@NoArgsConstructor
public class Timing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "started_in")
    private Instant startedIn;

    @ManyToOne
    @JoinColumn(name = "users_projects_id")
    private UserProject userProject;

    public String toString()
    {
        return "Timing [id=" + id + ", duration=" + duration;
    }

    public Timing(UserProject userProject)
    {
        this.userProject = userProject;
        duration = 0L;
        startedIn = Instant.now();
    }
}