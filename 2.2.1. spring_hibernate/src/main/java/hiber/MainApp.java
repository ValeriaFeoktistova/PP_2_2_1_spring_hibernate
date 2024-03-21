package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      User user1 = new User("Bob", "BobLast", "bob@mail.ru");
      User user2 = new User("Bob2", "BobLast2", "bob2@mail.ru");
      User user3 = new User("Bob3", "BobLast3", "bob3@mail.ru");
      Car car1 = new Car("Model", 2023);
      Car car2 = new Car("Model2", 2022);
      Car car3 = new Car("Model3", 2021);

      user1.setCar(car1);
      user2.setCar(car2);
      user3.setCar(car3);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println();

         Car car = user.getCar();
         if (car != null) {
            System.out.println("Model = " + car.getModel());
            System.out.println("Series = " + car.getSeries());
         } else {
            System.out.println("User has no car assigned.");
         }
         System.out.println("--------------------------------------------");
      }

      List<User> usersFind = userService.findUserByCarModelAndSeries("Model2", 2022);
      System.out.println("Получаем юзера, по модели и серии авто:");
      for (User user : usersFind) {
         System.out.println(user.getFirstName());
      }

      context.close();
   }
}

