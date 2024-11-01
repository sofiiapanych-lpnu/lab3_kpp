package SerializersTests;

import Models.Client;
import Models.Dish;
import Models.Order;
import Services.Serializers.YAMLSerializers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class YAMLSerializersTest {
    private YAMLSerializers yamlSerializers;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        yamlSerializers = new YAMLSerializers();
        testFilePath = "test_orders.yaml";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testReadWriteFile() {
        List<Order> orders = Arrays.asList(
                new Order(1, new Client("John", "Doe", "111-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pizza", 9.99), new Dish("Burger", 5.49)))),
                new Order(2, new Client("Mary", "Smith", "222-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pasta", 8.99), new Dish("Salad", 4.99))))
        );

        yamlSerializers.writeToFile(orders, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Файл повинен існувати після запису.");

        List<Order> loadedOrders = yamlSerializers.readFromFile(testFilePath);
        assertEquals(orders.size(), loadedOrders.size(), "Кількість замовлень повинна бути такою ж.");
        assertEquals(orders.getFirst().getClient().getFirstName(), loadedOrders.getFirst().getClient().getFirstName(), "Імена клієнтів повинні збігатися.");
        assertEquals(orders.getFirst().getClient().getLastName(), loadedOrders.getFirst().getClient().getLastName(), "Прізвища клієнтів повинні збігатися.");
        assertEquals(orders.getFirst().getClient().getPhoneNumber(), loadedOrders.getFirst().getClient().getPhoneNumber(), "Номери клієнтів повинні збігатися.");
        assertEquals(2, loadedOrders.getFirst().getDishes().size(), "Кількість страв повинна бути 2.");
    }

    @Test
    public void testReadFromFileWithEmptyFile() {
        List<Order> loadedOrders = yamlSerializers.readFromFile(testFilePath);
        assertTrue(loadedOrders.isEmpty(), "Список замовлень повинен бути порожнім для порожнього файлу.");
    }

    @Test
    public void testOrderWithMoreThanThreeDishes() {
        List<Order> orders = List.of(
                new Order(3, new Client("Alice", "Johnson", "333-222-4444"),
                        new ArrayList<>(Arrays.asList(
                                new Dish("Sushi", 15.99),
                                new Dish("Ramen", 12.99),
                                new Dish("Tempura", 10.99),
                                new Dish("Miso Soup", 5.99)
                        )))
        );

        yamlSerializers.writeToFile(orders, testFilePath);

        List<Order> loadedOrders = yamlSerializers.readFromFile(testFilePath);
        assertEquals(1, loadedOrders.size(), "Кількість замовлень повинна бути 1.");
        assertNull(loadedOrders.getFirst().getClient(), "Клієнт не повинен бути присутнім у замовленні.");
        assertEquals(4, loadedOrders.getFirst().getDishes().size(), "Кількість страв повинна бути 4.");
    }
}
