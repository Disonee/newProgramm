package ru.sapteh;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.User;
import ru.sapteh.service.UserService;

public class Text {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        UserService userService = new UserService(factory);

//        User user = new User("Vladimir", "Vladimirov", 20);
//        userService.update(user);

//          final User byId = userService.findById(1);
//        byId.setFirstName("X");
//        byId.setLastName("X");
//        byId.setAge(10);
//        userService.update(byId);

        userService.delete(userService.findById(2));

    }
}
