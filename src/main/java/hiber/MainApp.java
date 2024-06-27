package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("Anna", "Vasileva", "user1@mail.io");
      User user2 = new User("Natalya", "Ivanova", "user2@mail.io");
      User user3 = new User("Olga", "Petrova", "user3@mail.io");
      User user4 = new User("Svetlana", "Molodyx", "user4@mail.io");

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      Car volvo = new Car("Volvo", 9);
      Car audi = new Car("Audi", 8);
      Car kia = new Car("Kia", 7);
      Car bmw = new Car("BMW", 3);


      userService.add(user1.setCar(volvo).setUser(user1));
      userService.add(user2.setCar(audi).setUser(user2));
      userService.add(user3.setCar(kia).setUser(user3));
      userService.add(user4.setCar(bmw).setUser(user4));

      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      } // пользователи с машинами

      System.out.println(userService.getUserByCar("BMW", 3)); // достаем юзера, владеющего машиной по ее модели и серии

      try {
         User notFoundUser = userService.getUserByCar("GAZ", 4211);
      } catch (NoResultException e) {
         System.out.println("User not found"); // исключение, если нет такого юзера с такой машиной
      }
      context.close();
   }
}
