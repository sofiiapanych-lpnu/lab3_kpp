package IOStreamTests;

import Services.IOStream.ClientIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Models.Client;

import java.io.File;
import java.util.List;
import java.util.Arrays;

public class ClientIOTest {
    private ClientIO clientIO;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        clientIO = new ClientIO();
        testFilePath = "testClients.txt";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testWriteToFile() {
        List<Client> clients = Arrays.asList(
                new Client("John", "Doe", "111-222-3333"),
                new Client("Mary", "Smith", "222-222-3333")
        );

        clientIO.writeToFile(clients, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists(), "Файл повинен існувати після запису.");

        List<Client> loadedClients = clientIO.readFromFile(testFilePath);
        assertEquals(clients.size(), loadedClients.size(), "Кількість клієнтів повинна бути такою ж.");
        assertEquals(clients.get(0).getFirstName(), loadedClients.get(0).getFirstName(), "Імена клієнтів повинні збігатися.");
        assertEquals(clients.get(1).getLastName(), loadedClients.get(1).getLastName(), "Прізвища клієнтів повинні збігати.");
    }

    @Test
    public void testReadFromFile() {
        List<Client> clients = Arrays.asList(
                new Client("Alice", "Johnson", "333-222-1111"),
                new Client("Bob", "Brown", "444-333-2222")
        );

        clientIO.writeToFile(clients, testFilePath);

        List<Client> loadedClients = clientIO.readFromFile(testFilePath);

        assertEquals(clients.size(), loadedClients.size(), "Кількість клієнтів повинна бути такою ж.");
        assertEquals(clients.get(0).getFirstName(), loadedClients.get(0).getFirstName(), "Імена клієнтів повинні збігатися.");
        assertEquals(clients.get(1).getPhoneNumber(), loadedClients.get(1).getPhoneNumber(), "Номери телефонів повинні збігати.");
    }

    @Test
    public void testReadFromFileWithEmptyFile() {
        List<Client> loadedClients = clientIO.readFromFile(testFilePath);
        assertTrue(loadedClients.isEmpty(), "Список клієнтів повинен бути порожнім для порожнього файлу.");
    }
}
