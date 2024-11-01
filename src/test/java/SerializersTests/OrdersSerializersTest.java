package SerializersTests;

import Models.Client;
import Models.Dish;
import Models.Order;
import Services.IReadWrite;
import Services.Serializers.JSONSerializers;
import Services.Serializers.NativeSerializers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OrdersSerializersTest {
    private IReadWrite<Order> serializers;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        serializers = new NativeSerializers();
        testFilePath = "test_orders.dat";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    static Stream<Object[]> provideSerializers() {
        return Stream.of(
                new Object[]{new JSONSerializers(), "test_orders.json"},
                new Object[]{new NativeSerializers(),"test_orders.dat"}
        );
    }

    @ParameterizedTest
    @MethodSource("provideSerializers")
    public void testWriteAndReadFromFile(Object serializer, String fileName) {
        List<Order> orders = Arrays.asList(
                new Order(1, new Client("John", "Doe", "111-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pizza", 9.99), new Dish("Burger", 5.49)))),
                new Order(2, new Client("Mary", "Smith", "222-222-3333"), new ArrayList<>(Arrays.asList(new Dish("Pasta", 8.99), new Dish("Salad", 4.99))))
        );
        serializers = (IReadWrite<Order>) serializer;
        testFilePath = fileName;

        serializers.writeToFile(orders, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Файл повинен існувати після запису.");

        List<Order> loadedOrders = serializers.readFromFile(testFilePath);
        assertEquals(orders.size(), loadedOrders.size(), "Кількість замовлень повинна бути такою ж.");

        for (int i = 0; i < orders.size(); i++) {
            assertEquals(orders.get(i).getClient().getFirstName(), loadedOrders.get(i).getClient().getFirstName(), "Ім'я клієнта повинні збігатися для замовлення " + (i + 1));
            assertEquals(orders.get(i).getClient().getLastName(), loadedOrders.get(i).getClient().getLastName(), "Прізвище клієнта повинні збігатися для замовлення " + (i + 1));
            assertEquals(orders.get(i).getClient().getPhoneNumber(), loadedOrders.get(i).getClient().getPhoneNumber(), "Номер клієнта повинні збігатися для замовлення " + (i + 1));
            assertNull(loadedOrders.get(i).getDishes(), "Страви повинні бути відсутні для замовлення " + (i + 1));
        }
    }

    @ParameterizedTest
    @MethodSource("provideSerializers")
    public void testReadFromFileWithEmptyFile(Object serializer, String fileName) {
        serializers = (IReadWrite<Order>) serializer;
        testFilePath = fileName;

        List<Order> loadedOrders = serializers.readFromFile(testFilePath);
        assertTrue(loadedOrders.isEmpty(), "Список замовлень повинен бути порожнім для порожнього файлу.");
    }
}

