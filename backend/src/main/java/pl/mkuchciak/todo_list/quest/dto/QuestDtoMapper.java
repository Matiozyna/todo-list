package pl.mkuchciak.todo_list.quest.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mkuchciak.todo_list.quest.Quest;
import pl.mkuchciak.todo_list.user.AppUser;
import pl.mkuchciak.todo_list.user.AppUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestDtoMapper {

    private final AppUserRepository appUserRepository;
    public QuestDto map(Quest quest){
        QuestDto dto = new QuestDto();
        dto.setId(quest.getId());
        dto.setCategory(quest.getCategory());
        dto.setPeriodicity(quest.getPeriodicity());
        dto.setDescription(quest.getDescription());
        dto.setDateAdded(quest.getDateAdded());
        dto.setStartDate(quest.getStartDate());
        dto.setCompletionDates(quest.getCompletionDates());

        Long ownersId = quest.getUser().getId();
        dto.setOwnersId(ownersId);
        return dto;
    }

    public Optional<Quest> map(QuestDto dto){
        Optional<AppUser> userOptional = getAppUserById(dto.getOwnersId());
        if(userOptional.isEmpty())
            return Optional.empty();
        AppUser user = userOptional.get();

        Quest quest = new Quest();
        quest.setId(dto.getId());
        quest.setCategory(dto.getCategory());
        quest.setPeriodicity(dto.getPeriodicity());
        quest.setDescription(dto.getDescription());
        quest.setDateAdded(dto.getDateAdded());
        quest.setStartDate(dto.getStartDate());
        quest.setCompletionDates(dto.getCompletionDates());
        quest.setUser(user);

        return Optional.of(quest);
    }

    private Optional<AppUser> getAppUserById(Long id){
        return appUserRepository.findById(id);
    }
}
