package Services.Serializers;
import Models.Order;

import Services.IReadWrite;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.DumperOptions;

public class YAMLSerializers implements IReadWrite<Order> {
    @Override
    public void writeToFile(List<Order> orders, String filePath) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        List<Object> ordersToWrite = new ArrayList<>();

        for (Order order : orders) {
            if (order.getDishes().size() > 3) {
                Order orderWithoutClient = order.cloneOrderWithoutClients();
                ordersToWrite.add(orderWithoutClient);
            } else {
                ordersToWrite.add(order);
            }
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(ordersToWrite, writer);
            System.out.println("Замовлення успішно записано у YAML файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у YAML файл.");
        }
    }

    @Override
    public List<Order> readFromFile(String filePath) {
        Yaml yaml = new Yaml(new Constructor(List.class));
        List<Order> orders = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            List<Order> loadedOrders = yaml.load(reader);
            orders.addAll(loadedOrders);
            System.out.println("Замовлення успішно зчитано з YAML файлу.");
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні з YAML файлу: " + e.getMessage());
        }
        return orders;
    }
}
