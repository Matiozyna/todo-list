package pl.mkuchciak.todo_list.quest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkuchciak.todo_list.quest.dto.QuestDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/quests")
@AllArgsConstructor
public class QuestController {

    private final QuestService questService;
    @GetMapping("/{id}")
    ResponseEntity<QuestDto> getQuestById(@PathVariable Long id){
        return questService.getQuestById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<QuestDto> saveQuest(@RequestBody QuestDto questDto){
        if (questDto.getId()!=null){
            return ResponseEntity.badRequest().build();
        }
        else {
            return  questService.saveQuest(questDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
        }
    }
}
