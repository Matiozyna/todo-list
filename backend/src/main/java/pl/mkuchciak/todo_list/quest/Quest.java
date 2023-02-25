package pl.mkuchciak.todo_list.quest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.mkuchciak.todo_list.quest.enums.QuestCategory;
import pl.mkuchciak.todo_list.quest.enums.QuestPeriodicity;
import pl.mkuchciak.todo_list.user.AppUser;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quest")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private QuestCategory category;

    @Column(name = "description")
    private String description;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "periodicity")
    @Enumerated(EnumType.ORDINAL)
    private QuestPeriodicity periodicity;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private AppUser user;

   @ElementCollection(fetch = FetchType.EAGER)
   private Set<LocalDate> completionDates;

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", dateAdded=" + dateAdded +
                ", startDate=" + startDate +
                ", periodicity=" + periodicity +
                ", user=" + user.getDisplayName() +
                ", completionDates=" + completionDates +
                '}';
    }
}