package pl.mkuchciak.todo_list.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mkuchciak.todo_list.quest.Quest;
import pl.mkuchciak.todo_list.quest.QuestRepository;
import pl.mkuchciak.todo_list.user.dto.AppUserDto;
import pl.mkuchciak.todo_list.user.dto.AppUserDtoMapper;
import pl.mkuchciak.todo_list.user.dto.AppUserPutMethodDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDtoMapper appUserDtoMapper;
    private final QuestRepository questRepository;
    public Optional<AppUserDto> getUserDtoById(Long id){
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isEmpty())
            return Optional.empty();
        AppUser user = userOptional.get();
        AppUserDto dto = appUserDtoMapper.map(user);
        return Optional.of(dto);
    }

    public Set<Quest> getAllUserQuests(Long id){
        Optional<AppUser> userOpt = appUserRepository.findById(id);
        if(userOpt.isPresent()){
            AppUser user = userOpt.get();
            return user.getQuests();
        }
        return Set.of();
    }

    @Transactional
    public Optional<AppUserDto> deleteUserById(Long id){
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isEmpty())
            return Optional.empty();
        AppUser appUser = userOptional.get();
        AppUserDto dto = appUserDtoMapper.map(appUser);
        questRepository.deleteAllByUser(appUser);
        appUserRepository.delete(appUser);
        return Optional.of(dto);
    }

    public Optional<AppUserDto> saveUser(AppUserDto dto){
        AppUser user = appUserDtoMapper.map(dto);
        if(user.getId()!=null)
            return Optional.empty();
        user.setRegisterDate(LocalDate.now());
        AppUser savedUser = appUserRepository.save(user);
        return Optional.of(appUserDtoMapper.map(savedUser));
    }

    public Optional<AppUserDto> replaceUser(Long id, AppUserPutMethodDto dto){
        if(!appUserRepository.existsById(id))
            return Optional.empty();
        AppUser user = appUserRepository.findById(id).orElseThrow();
        AppUser updatedUser = applyChanges(user, dto);
        return Optional.of(appUserDtoMapper.map(appUserRepository.save(updatedUser)));
    }

    public void updateUser(AppUserDto dto){
        AppUser user = appUserRepository.findById(dto.getId()).orElseThrow();
        AppUser patchedUser = applyChanges(user, dto);
        appUserRepository.save(patchedUser);
    }
    private AppUser applyChanges(AppUser user, AppUserPutMethodDto dto){
        user.setPassword(dto.getPassword());
        user.setLevel(dto.getLevel());
        user.setExperience(dto.getExperience());
        user.setUsername(dto.getUsername());
        user.setDisplayName(dto.getDisplayName());
        return user;
    }

    private AppUser applyChanges(AppUser user, AppUserDto dto){
        user.setPassword(dto.getPassword());
        user.setLevel(dto.getLevel());
        user.setExperience(dto.getExperience());
        user.setUsername(dto.getUsername());
        user.setDisplayName(dto.getDisplayName());
        return user;
    }

}
