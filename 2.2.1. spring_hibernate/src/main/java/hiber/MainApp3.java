package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp3 {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);

        Car car1 = new Car("Model", 2023);
        Car car2 = new Car("Model2", 2022);
        Car car3 = new Car("Model3", 2021);
        carService.add(car1);// Сначала создаём машины, сохраняем их в бд
        carService.add(car2);
        carService.add(car3);

        List<Car> cars = carService.listCars();

        Car car4 = cars.get(0);
        Car car5 = cars.get(1);
        Car car6 = cars.get(2);

        User user1 = new User("User1", "Lastname1", "user1@mail.ru", car4);
        User user2 = new User("User2", "Lastname2", "user2@mail.ru", car5);
        User user3 = new User("User3", "Lastname3", "user3@mail.ru", car6);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);

        userService.findUserByCarModelAndSeries("Model2", 2022);//5. В сервис добавьте метод, который с
        // помощью hql-запроса будет доставать юзера, владеющего машиной по ее модели и серии(Получаем юзера,по модели и серии авто)
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("----------------------------");
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar().getModel() + " " + user.getCar().getSeries());
            System.out.println();

            System.out.println("Получаем юзера, по модели и серии авто:");
            System.out.println("User: " + user.getFirstName() + ", Car: " + user.getCar().getModel() + " " + user.getCar().getSeries());
        }

        context.close();

    }
}



/*
1. Нет связи между
        serService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        См.
        См.
        и
        User user1 = new User("Bob", "BobLast", "bob@mail.ru");
        См.
        См.
        Если уже сохранила юзеров в бд, то вытащи их обратно и раздай им имашины.
        2. Но лучше реализовать по другому, для практики работы с бд.
        Сначала создаём машины, сохраняем их в бд, получаем список машин из бд и при создании юзеров, раздаём и машины.
        Получаем список юзеров из бд вместе с машинами
        Бины для каждой сущности создаём/используем разные. Т.е. нельзя через context.getBean(UserService.class) сохранять
         и вытаскивать машины



*/

