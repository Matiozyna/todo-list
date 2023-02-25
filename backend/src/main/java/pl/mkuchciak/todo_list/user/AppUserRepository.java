package pl.mkuchciak.todo_list.user;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository <AppUser, Long> {

}
