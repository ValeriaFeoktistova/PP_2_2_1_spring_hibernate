package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp2 {
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

        userService.add(new User("User7", "Lastname1", "user1@mail.ru", car1)); //и при создании юзеров, раздаём и машины.
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", car3));

        List<User> users = userService.listUsers();
        List<Car> cars = carService.listCars();

        int carIndex = 0;//
        for (User user : users) {
            if (carIndex < cars.size()) {
                user.setCar(cars.get(carIndex)); // Присваиваем автомобиль пользователю
                userService.updateUser(user);// Обновляем пользователя в базе данных, это метод обязателен????????!!!!
                // не вижу в нём здесь смысла- без него всё так же работает
                carIndex++;
            } else {
                // Если автомобилей недостаточно, прерываем цикл
                break;
            }
        }
        List<User> usersFind = userService.findUserByCarModelAndSeries("Model2", 2022);//5. В сервис добавьте метод, который с
        // помощью hql-запроса будет доставать юзера, владеющего машиной по ее модели и серии(Получаем юзера,по модели и серии авто)

        for (User user : users) {
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
        Бины для каждой сущности создаём/используем разные. Т.е. нельзя через context.getBean(UserService.class) сохранять и вытаскивать машины



*/
