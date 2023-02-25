package pl.mkuchciak.todo_list.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import pl.mkuchciak.todo_list.quest.Quest;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AppUserDto {
    private Long id;
    private String displayName;
    private Integer level;
    private Integer experience;
    private LocalDate registerDate;

    private String username;
    private String password;
    private Set<Quest> activeQuests = new LinkedHashSet<>();


    public AppUserDto( String displayName, Integer level, Integer experience, LocalDate registerDate, Set<Quest> quests) {
        this.displayName = displayName;
        this.level = level;
        this.experience = experience;
        this.registerDate = registerDate;
        this.activeQuests = quests;
    }

    public AppUserDto( String displayName, Integer level, Integer experience, LocalDate registerDate) {
        this.displayName = displayName;
        this.level = level;
        this.experience = experience;
        this.registerDate = registerDate;
    }
}
