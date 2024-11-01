package Services.Serializers;

import Models.Dish;
import Models.Order;
import Services.IOStream.DishIO;
import Services.IReadWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NativeSerializers implements IReadWrite<Order> {
    @Override
    public void writeToFile(List<Order> orders, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Order order : orders) {
                oos.writeObject(order);
            }
            System.out.println("Список замовлень успішно записано у файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі списку замовлень у файл: " + e.getMessage());
        }
    }

    @Override
    public List<Order> readFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Order order = (Order) ois.readObject();
                    orders.add(order);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Список замовлень успішно зчитано з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка при зчитуванні списку замовлень з файлу: " + e.getMessage());
        }
        return orders;
    }
}
