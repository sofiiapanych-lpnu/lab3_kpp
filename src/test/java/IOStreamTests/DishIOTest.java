package IOStreamTests;

import Services.IOStream.DishIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Models.Dish;

import java.io.File;
import java.util.List;
import java.util.Arrays;

public class DishIOTest {
    private DishIO dishIO;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        dishIO = new DishIO();
        testFilePath = "testDishes.txt"; // Шлях до тестового файлу
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete(); // Видалити файл після кожного тесту
        }
    }

    @Test
    public void testWriteToFile() {
        List<Dish> dishes = Arrays.asList(
                new Dish("Pizza", 9.99),
                new Dish("Burger", 5.49)
        );

        dishIO.writeToFile(dishes, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Файл повинен існувати після запису.");

        List<Dish> loadedDishes = dishIO.readFromFile(testFilePath);
        assertEquals(dishes.size(), loadedDishes.size(), "Кількість страв повинна бути такою ж.");
        assertEquals(dishes.get(0).getName(), loadedDishes.get(0).getName(), "Назви страв повинні збігатися.");
        assertEquals(dishes.get(1).getPrice(), loadedDishes.get(1).getPrice(), "Ціни страв повинні збігати.");
    }

    @Test
    public void testReadFromFile() {
        List<Dish> dishes = Arrays.asList(
                new Dish("Pasta", 8.99),
                new Dish("Salad", 4.99)
        );

        dishIO.writeToFile(dishes, testFilePath);

        List<Dish> loadedDishes = dishIO.readFromFile(testFilePath);

        assertEquals(dishes.size(), loadedDishes.size(), "Кількість страв повинна бути такою ж.");
        assertEquals(dishes.get(0).getName(), loadedDishes.get(0).getName(), "Назви страв повинні збігатися.");
        assertEquals(dishes.get(1).getPrice(), loadedDishes.get(1).getPrice(), "Ціни страв повинні збігати.");
    }

    @Test
    public void testReadFromFileWithEmptyFile() {
        List<Dish> loadedDishes = dishIO.readFromFile(testFilePath);
        assertTrue(loadedDishes.isEmpty(), "Список страв повинен бути порожнім для порожнього файлу.");
    }
}
