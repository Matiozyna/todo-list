package pl.mkuchciak.todo_list.quest;

import org.springframework.data.repository.CrudRepository;
import pl.mkuchciak.todo_list.user.AppUser;

import java.util.Set;

public interface QuestRepository extends CrudRepository<Quest, Long> {
    void deleteAllByUser(AppUser appUser);
    Set<Quest> findAllByUserId(Long id);
}
