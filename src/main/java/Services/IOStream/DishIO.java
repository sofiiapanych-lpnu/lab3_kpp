package Services.IOStream;

import Models.Dish;
import Services.IReadWrite;

import java.io.*;
import java.util.*;

public class DishIO implements IReadWrite<Dish> {
    @Override
    public void writeToFile(List<Dish> dishes, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             PrintWriter writer = new PrintWriter(osw)) {
            for (Dish dish : dishes) {
                writer.println(dish.getName() + ", " + dish.getPrice());
            }
            System.out.println("Список страв успішно записано у файл");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл");
        }
    }

    @Override
    public List<Dish> readFromFile(String filePath) {
        List<Dish> dishes = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    dishes.add(new Dish(name, price));
                }
            }
            System.out.println("Список страв успішно зчитано з файлу");
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні з файлу");
        }
        return dishes;
    }
}
