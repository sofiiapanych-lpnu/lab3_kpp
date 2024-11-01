package IOStreamTests;

import Models.Client;
import Services.IOStream.OrderIO;
import Models.Order;
import Models.Dish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderIOTest {
    private OrderIO orderIO;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        orderIO = new OrderIO();
        testFilePath = "testOrders.txt"; // Шлях до тестового файлу
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete(); // Видалити файл після кожного тесту
        }

        for (int i = 0; i < 100; i++) {
            File dishFile = new File("dishes_order" + i + ".txt");
            if (dishFile.exists()) {
                dishFile.delete();
            }
        }
    }

    @Test
    public void testWriteToFile() {
        List<Order> orders = Arrays.asList(
                new Order(1, new Client("John", "Doe", "111-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pizza", 9.99), new Dish("Burger", 5.49)))),
                new Order(2, new Client("Mary", "Smith", "222-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pasta", 8.99), new Dish("Salad", 4.99))))
        );

        orderIO.writeToFile(orders, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Файл повинен існувати після запису.");

        List<Order> loadedOrders = orderIO.readFromFile(testFilePath);
        assertEquals(orders.size(), loadedOrders.size(), "Кількість замовлень повинна бути такою ж.");

        for (int i = 0; i < orders.size(); i++) {
            assertEquals(orders.get(i).getOrderID(), loadedOrders.get(i).getOrderID(), "ID замовлень повинні збігатися.");
            assertEquals(orders.get(i).getDishes().size(), loadedOrders.get(i).getDishes().size(), "Кількість страв у замовленнях повинна бути такою ж.");
            assertEquals(orders.get(i).getDishes().getFirst().getName(), loadedOrders.get(i).getDishes().get(0).getName(), "Назви страв повинні збігатися.");
        }
    }

    @Test
    public void testReadFromFile() {
        List<Order> orders = Arrays.asList(
                new Order(1, new Client("John", "Doe", "111-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pizza", 9.99), new Dish("Burger", 5.49)))),
                new Order(2, new Client("Mary", "Smith", "222-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pasta", 8.99), new Dish("Salad", 4.99))))
        );

        orderIO.writeToFile(orders, testFilePath);

        List<Order> loadedOrders = orderIO.readFromFile(testFilePath);

        assertEquals(orders.size(), loadedOrders.size(), "Кількість замовлень повинна бути такою ж.");
        for (int i = 0; i < orders.size(); i++) {
            assertEquals(orders.get(i).getOrderID(), loadedOrders.get(i).getOrderID(), "ID замовлень повинні збігатися.");
            assertEquals(orders.get(i).getDishes().size(), loadedOrders.get(i).getDishes().size(), "Кількість страв у замовленнях повинна бути такою ж.");
            assertEquals(orders.get(i).getDishes().getFirst().getName(), loadedOrders.get(i).getDishes().get(0).getName(), "Назви страв повинні збігатися.");
        }
    }

    @Test
    public void testReadFromFileWithEmptyFile() {
        List<Order> loadedOrders = orderIO.readFromFile(testFilePath);
        assertTrue(loadedOrders.isEmpty(), "Список замовлень повинен бути порожнім для порожнього файлу.");
    }

    @Test
    public void testWriteAndReadComplexOrder() {
        List<Dish> dishes = Arrays.asList(
                new Dish("Cheeseburger", 7.99),
                new Dish("Fries", 2.99)
        );
        Order order = new Order(1, new Client("John", "Doe", "111-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Cheeseburger", 7.99), new Dish("Fries", 2.99))));

        orderIO.writeToFile(List.of(order), testFilePath);
        List<Order> loadedOrders = orderIO.readFromFile(testFilePath);

        assertEquals(1, loadedOrders.size(), "Кількість завантажених замовлень повинна бути 1.");
        assertEquals(order.getOrderID(), loadedOrders.getFirst().getOrderID(), "ID замовлення повинно збігатися.");
        assertEquals(dishes.size(), loadedOrders.getFirst().getDishes().size(), "Кількість страв повинна бути такою ж.");
        assertEquals(dishes.getFirst().getName(), loadedOrders.getFirst().getDishes().getFirst().getName(), "Назви страв повинні збігатися.");
    }

    @Test
    public void testInvalidFilePath() {
        String invalidFilePath = "invalid/path/to/orders.txt";
        List<Order> loadedOrders = orderIO.readFromFile(invalidFilePath);
        assertTrue(loadedOrders.isEmpty(), "Список замовлень повинен бути порожнім для недоступного файлу.");
    }
}
