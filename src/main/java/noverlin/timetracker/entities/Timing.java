package noverlin.timetracker.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Table(name = "project")
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
    private Instant created_in;

    @Column(name = "all_time_spent")
    private Instant all_time_spent;

    public String toString()
    {
        return "Project [id=" + id + ", title=" + title + ", description=" + description + ", finished=" + finished;
    }
}