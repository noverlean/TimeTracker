package noverlin.timetracker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserProject> userProjects;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    public String toString()
    {
        return "=== USER ================\n\t" +
                "id: " + id + ", \n\t" +
                "name: " + name + ", \n\t" +
                "password: " + password + ", \n\t" +
                "roles: " + roles.stream().map(Role::toString);
    }
}
