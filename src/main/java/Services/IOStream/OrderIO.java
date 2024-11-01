package Services.IOStream;

import Models.Client;
import Models.Dish;
import Models.Order;
import Services.IReadWrite;

import java.io.*;
import java.util.*;

public class OrderIO implements IReadWrite<Order> {
    DishIO dishIO = new DishIO();

    @Override
    public void writeToFile(List<Order> orders, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Order order : orders) {
                oos.writeObject(order);
                String dishesFilePath = "dishes_order" + order.getOrderID() + ".txt";
                dishIO.writeToFile(order.getDishes(), dishesFilePath);
            }
            System.out.println("Замовлення успішно записані у файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі замовлень у файл.");
        }
    }

    @Override
    public List<Order> readFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Order order = (Order) ois.readObject();
                    String dishesFilePath = "dishes_order" + order.getOrderID() + ".txt";
                    List<Dish> dishes = dishIO.readFromFile(dishesFilePath);
                    order.setDishes(dishes);
                    orders.add(order);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Замовлення успішно зчитано з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка при зчитуванні замовлень з файлу.");
        }
        return orders;
    }
}
