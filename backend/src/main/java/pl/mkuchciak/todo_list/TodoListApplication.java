package pl.mkuchciak.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.mkuchciak.todo_list.quest.Quest;
import pl.mkuchciak.todo_list.quest.QuestRepository;
import pl.mkuchciak.todo_list.user.AppUser;
import pl.mkuchciak.todo_list.user.AppUserRepository;

@SpringBootApplication
public class TodoListApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TodoListApplication.class, args);
        QuestRepository questRepository = context.getBean(QuestRepository.class);
        AppUserRepository appUserRepository = context.getBean(AppUserRepository.class);

        for (AppUser appUser : appUserRepository.findAll()) {
            System.out.println(appUser);
        }

        for (Quest quest : questRepository.findAll()) {
            System.out.println(quest);
        }


    }

}
