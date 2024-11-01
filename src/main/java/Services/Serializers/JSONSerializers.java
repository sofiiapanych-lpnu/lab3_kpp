package Services.Serializers;

import Models.Order;
import Services.IReadWrite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class JSONSerializers implements IReadWrite<Order> {
    Gson gson;
    public JSONSerializers(){
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public void writeToFile(List<Order> orders, String filePath) {
        String json = gson.toJson(orders);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            System.out.println("Замовлення успішно записані у JSON файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у JSON файл.");
        }
    }

    @Override
    public List<Order> readFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Type orderListType = new TypeToken<List<Order>>() {}.getType();
            orders = gson.fromJson(reader, orderListType);
            System.out.println("Замовлення успішно зчитані з JSON файлу.");
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні з JSON файлу.");
        }
        return orders;
    }
}
