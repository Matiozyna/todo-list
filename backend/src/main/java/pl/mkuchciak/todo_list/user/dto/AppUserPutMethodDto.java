package pl.mkuchciak.todo_list.user.dto;

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
public class AppUserPutMethodDto {
    private String displayName;
    private Integer level;
    private Integer experience;
    private String username;
    private String password;
}
