package Services;

import Models.Client;
import Models.Dish;
import Models.Order;
import Services.IOStream.*;
import Services.Serializers.JSONSerializers;
import Services.Serializers.NativeSerializers;
import Services.Serializers.YAMLSerializers;

import java.util.List;

public class DataManager {
    public void manageClients() {
        ClientIO clientIO = new ClientIO();
        DataService<Client> clientService = new DataService<>();
        clientService.setReaderWriter(clientIO);

        List<Client> clients = List.of(
                new Client("John", "Doe", "111-222-3333"),
                new Client("Mary", "Smith", "222-222-3333"),
                new Client("Jane", "Smith", "222-222-3333")
        );

        String clientFilePath = "clients.txt";
        clientService.saveData(clients, clientFilePath);
        List<Client> loadedClients = clientService.loadData(clientFilePath);
        System.out.println("Зчитані клієнти: " + loadedClients);
    }

    public void manageDishes() {
        DishIO dishIO = new DishIO();
        DataService<Dish> dishService = new DataService<>();
        dishService.setReaderWriter(dishIO);

        List<Dish> dishes = List.of(
                new Dish("Pizza Margarita", 200),
                new Dish("Carbonara", 195),
                new Dish("Cheeseburger", 65),
                new Dish("Tea", 60)
        );

        String dishFilePath = "dishes.txt";
        dishService.saveData(dishes, dishFilePath);
        List<Dish> loadedDishes = dishService.loadData(dishFilePath);
        System.out.println("Зчитані страви: " + loadedDishes);
    }

    public void manageOrders(List<Order> orders) {
        saveOrdersWithIOStream(orders);
        saveOrdersWithNativeSerializer(orders);
        saveOrdersWithJSONSerializer(orders);
        saveOrdersWithYAMLSerializer(orders);
    }

    public void saveOrdersWithIOStream(List<Order> orders) {
        OrderIO orderIOStream = new OrderIO();
        DataService<Order> orderServiceIO = new DataService<>();
        orderServiceIO.setReaderWriter(orderIOStream);

        String nativeFilePath = "orders.txt";
        orderServiceIO.saveData(orders, nativeFilePath);
        List<Order> loadedOrdersNative = orderServiceIO.loadData(nativeFilePath);
        System.out.println("Зчитані замовлення (IO Stream): " + loadedOrdersNative);
    }

    public void saveOrdersWithNativeSerializer(List<Order> orders) {
        NativeSerializers nativeSerializer = new NativeSerializers();
        DataService<Order> orderServiceNative = new DataService<>();
        orderServiceNative.setReaderWriter(nativeSerializer);

        String nativeFilePath = "orders_native.dat";
        orderServiceNative.saveData(orders, nativeFilePath);
        List<Order> loadedOrdersNative = orderServiceNative.loadData(nativeFilePath);
        System.out.println("Зчитані замовлення (Native): " + loadedOrdersNative);
    }

    public void saveOrdersWithJSONSerializer(List<Order> orders) {
        JSONSerializers jsonSerializer = new JSONSerializers();
        DataService<Order> orderServiceJSON = new DataService<>();
        orderServiceJSON.setReaderWriter(jsonSerializer);

        String jsonFilePath = "orders.json";
        orderServiceJSON.saveData(orders, jsonFilePath);
        List<Order> loadedOrdersJSON = orderServiceJSON.loadData(jsonFilePath);
        System.out.println("Зчитані замовлення (JSON): " + loadedOrdersJSON);
    }

    public void saveOrdersWithYAMLSerializer(List<Order> orders) {
        YAMLSerializers yamlSerializer = new YAMLSerializers();
        DataService<Order> orderServiceYAML = new DataService<>();
        orderServiceYAML.setReaderWriter(yamlSerializer);

        String yamlFilePath = "orders.yaml";
        orderServiceYAML.saveData(orders, yamlFilePath);
        List<Order> loadedOrdersYAML = orderServiceYAML.loadData(yamlFilePath);
        System.out.println("Зчитані замовлення (YAML): " + loadedOrdersYAML);
    }
}
