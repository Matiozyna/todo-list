package pl.mkuchciak.todo_list.quest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.mkuchciak.todo_list.quest.enums.QuestCategory;
import pl.mkuchciak.todo_list.quest.enums.QuestPeriodicity;
import pl.mkuchciak.todo_list.user.AppUser;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuestDto {

    private Long id;
    private QuestCategory category;
    private QuestPeriodicity periodicity;
    private String description;
    private LocalDate dateAdded;
    private LocalDate startDate;
    private Long ownersId;
    private Set<LocalDate> completionDates;


}
