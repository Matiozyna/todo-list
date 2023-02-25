package pl.mkuchciak.todo_list.user.dto;

import org.springframework.stereotype.Service;
import pl.mkuchciak.todo_list.user.AppUser;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class AppUserDtoMapper {

    public AppUser mapFromPutMethodDto(AppUserPutMethodDto dto){
        AppUser user = new AppUser();
        user.setPassword(dto.getPassword());
        user.setLevel(dto.getLevel());
        user.setExperience(dto.getExperience());
        user.setUsername(dto.getUsername());
        user.setDisplayName(dto.getDisplayName());
        return user;
    }

    public AppUserDto map(AppUser user){
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getId());
        dto.setDisplayName(user.getDisplayName());
        dto.setLevel(user.getLevel());
        dto.setExperience(user.getExperience());
        dto.setRegisterDate(user.getRegisterDate());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setActiveQuests(user.getQuests().stream().filter(q -> q.getStartDate().equals(LocalDate.now())).collect(Collectors.toSet()));
        return dto;
    }

    public AppUser map(AppUserDto dto){
        AppUser user = new AppUser();
        user.setId(dto.getId());
        user.setExperience(dto.getExperience());
        user.setLevel(dto.getLevel());
        user.setDisplayName(dto.getDisplayName());
        user.setRegisterDate(dto.getRegisterDate());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;
    }
}
