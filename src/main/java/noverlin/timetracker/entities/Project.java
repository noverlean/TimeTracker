package noverlin.timetracker.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "projects")
@Accessors(chain = true)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "created_in")
    private Instant createdIn;

    @Column(name = "all_time_spent")
    private Long allTimeSpent;

    @OneToMany(mappedBy = "project")
    private List<UserProject> userProjects;

    public String toString()
    {
        return "Project [id=" + id + ", title=" + title + ", description=" + description + ", finished=" + finished;
    }
}