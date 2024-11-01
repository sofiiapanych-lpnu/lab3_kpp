package Services.IOStream;

import Models.Client;
import Services.IReadWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientIO implements IReadWrite<Client> {
    @Override
    public void writeToFile(List<Client> clients, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(bos)) {

            for (Client client : clients) {
                writer.write(client.getFirstName() + ", " + client.getLastName() + ", " + client.getPhoneNumber());
                writer.write("\n");
            }
            System.out.println("Список клієнтів успішно записано у файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }

    @Override
    public List<Client> readFromFile(String filePath) {
        List<Client> clients = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader reader = new InputStreamReader(bis);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String phoneNumber = parts[2];
                    clients.add(new Client(firstName, lastName, phoneNumber));
                }
            }
            System.out.println("Список клієнтів успішно зчитано з файлу.");
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні з файлу: " + e.getMessage());
        }
        return clients;
    }
}
