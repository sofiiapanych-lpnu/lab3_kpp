package Initializers;

import Models.Client;
import Models.Dish;
import Models.Order;

import java.util.ArrayList;
import java.util.List;

public class DefaultDataInitializer {
    public static List<Order> getDefaultOrders() {
        Client client1 = new Client("John", "Doe", "111-222-3333");
        Client client2 = new Client("Mary", "Smith", "222-222-3333");
        Client client3 = new Client("Jane", "Smith", "222-222-3333");

        Dish dish1 = new Dish("Pizza Margarita", 200);
        Dish dish2 = new Dish("Carbonara", 195);
        Dish dish3 = new Dish("Cheeseburger", 65);
        Dish dish4 = new Dish("Tea", 60);

        List<Dish> dishes1 = new ArrayList<>();
        dishes1.add(dish1);
        dishes1.add(dish2);
        dishes1.add(dish3);

        List<Dish> dishes2 = new ArrayList<>();
        dishes2.add(dish3);
        dishes2.add(dish4);

        List<Dish> dishes3 = new ArrayList<>();
        dishes2.add(dish3);

        Order order1 = new Order(1, client1, dishes1);
        Order order2 = new Order(2, client2, dishes2);
        Order order3 = new Order(3, client3, dishes3);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        return orders;
    }
}
