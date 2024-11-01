package Services;

import java.util.List;

public class DataService<T> {
    private IReadWrite<T> readerWriter;

    public void setReaderWriter(IReadWrite<T> readerWriter) {
        this.readerWriter = readerWriter;
    }

    public void saveData(List<T> data, String filePath) {
        if (readerWriter != null) {
            readerWriter.writeToFile(data, filePath);
        } else {
            System.out.println("Сервіс читання/запису не налаштовано.");
        }
    }

    public List<T> loadData(String filePath) {
        if (readerWriter != null) {
            return readerWriter.readFromFile(filePath);
        } else {
            System.out.println("Сервіс читання/запису не налаштовано.");
            return null;
        }
    }
}
