package pl.mkuchciak.todo_list.quest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mkuchciak.todo_list.quest.dto.QuestDto;
import pl.mkuchciak.todo_list.quest.dto.QuestDtoMapper;
import pl.mkuchciak.todo_list.user.AppUserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestDtoMapper questDtoMapper;
    private final AppUserRepository appUserRepository;

    public Optional<Set<QuestDto>> getAllQuestsByUsersId(Long userId){
        if(!appUserRepository.existsById(userId))
            return Optional.empty();
        return Optional.of(
                questRepository.findAllByUserId(userId)
                        .stream()
                        .map(questDtoMapper::map)
                        .collect(Collectors.toSet()));
    }

    public Optional<QuestDto> getQuestById(Long id){
        return questRepository.findById(id).map(questDtoMapper::map);
    }
    
    public Optional<QuestDto> saveQuest(QuestDto dto){
        Optional<Quest> questOpt = questDtoMapper.map(dto);
        if(questOpt.isPresent()){
            Quest quest = questOpt.get();
            quest.setDateAdded(LocalDate.now());
            quest.setCompletionDates(Set.of());
            Quest savedQuest = questRepository.save(quest);
            return Optional.of(questDtoMapper.map(savedQuest));
        }
        return Optional.empty();
    }

}
