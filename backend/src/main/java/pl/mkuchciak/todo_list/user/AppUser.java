package pl.mkuchciak.todo_list.user;

import jakarta.persistence.*;
import lombok.*;
import pl.mkuchciak.todo_list.quest.Quest;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "level")
    private Integer level;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "register_date")
    private LocalDate registerDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Quest> quests = new LinkedHashSet<>();

    public AppUser(String username, String password, String displayName, Integer level, Integer experience, LocalDate registerDate, Set<Quest> quests) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.level = level;
        this.experience = experience;
        this.registerDate = registerDate;
        this.quests = quests;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                ", level=" + level +
                ", experience=" + experience +
                ", registerDate=" + registerDate +
                '}';
    }
}