package noverlin.timetracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Table(name = "users-projects")
@Accessors(chain = true)
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "userProject")
    private List<Timing> timings;

    public String toString()
    {
        return "=== UserProject ================\n\t" +
                "id: " + id + ", \n\t" +
                "some fields was hidden";
    }
}