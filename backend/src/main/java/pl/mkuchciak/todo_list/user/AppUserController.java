package pl.mkuchciak.todo_list.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mkuchciak.todo_list.quest.Quest;
import pl.mkuchciak.todo_list.quest.QuestService;
import pl.mkuchciak.todo_list.quest.dto.QuestDto;
import pl.mkuchciak.todo_list.user.dto.AppUserDto;
import pl.mkuchciak.todo_list.user.dto.AppUserPutMethodDto;

import java.net.URI;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@CrossOrigin
public class AppUserController {

    private final AppUserService appUserService;
    private final ObjectMapper objectMapper;
    private final QuestService questService;
    @GetMapping("/{id}")
    ResponseEntity<AppUserDto> getUserById(@PathVariable Long id){
        Optional<AppUserDto> userDtoOptional = appUserService.getUserDtoById(id);
        return userDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/displayName")
    ResponseEntity<String> getUserDisplayNameById(@PathVariable Long id){
        Optional<AppUserDto> userDtoOptional = appUserService.getUserDtoById(id);
        return userDtoOptional.map(AppUserDto::getDisplayName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/level")
    ResponseEntity<Integer> getUserLevelById(@PathVariable Long id){
        Optional<AppUserDto> userDtoOptional = appUserService.getUserDtoById(id);
        return userDtoOptional.map(AppUserDto::getLevel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/experience")
    ResponseEntity<Integer> getUserExperienceById(@PathVariable Long id){
        Optional<AppUserDto> userDtoOptional = appUserService.getUserDtoById(id);
        return userDtoOptional.map(AppUserDto::getExperience).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/quests")
    ResponseEntity<Set<QuestDto>> getUserActiveQuestsById(@PathVariable Long id, @RequestParam(required = false) LocalDate date){
        Optional<Set<QuestDto>> questsOpt = questService.getAllQuestsByUsersId(id);
        if(questsOpt.isEmpty())
            return ResponseEntity.notFound().build();
        Set<QuestDto> quests = questsOpt.get();
        if (date==null){
            return ResponseEntity.ok(quests);
        }else{
            Set<QuestDto> questsFiltered = quests.stream().filter(q -> q.getStartDate().equals(date)).collect(Collectors.toSet());
            return ResponseEntity.ok(questsFiltered);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<AppUserDto> deleteUserById(@PathVariable Long id){
        Optional<AppUserDto> userDtoOptional = appUserService.deleteUserById(id);
        return userDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<AppUserDto> saveUser(@RequestBody AppUserDto dto){
        Optional<AppUserDto> dtoOptional = appUserService.saveUser(dto);
        if(dtoOptional.isPresent()){
            AppUserDto savedDto = dtoOptional.get();
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedDto.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(savedDto);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceUser(@PathVariable Long id,@RequestBody AppUserPutMethodDto dto){
        return appUserService.replaceUser(id, dto)
                .map(u -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody JsonMergePatch patch){
        try{
            AppUserDto appUser = appUserService.getUserDtoById(id).orElseThrow();
            AppUserDto userPatch = applyPatch(appUser, patch);
            appUserService.updateUser(userPatch);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();

    }

    private AppUserDto applyPatch(AppUserDto appUser, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode userNode = objectMapper.valueToTree(appUser);
        JsonNode userPatchedNode = patch.apply(userNode);
        return objectMapper.treeToValue(userPatchedNode, AppUserDto.class);
    }


}
