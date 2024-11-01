package Services;

import java.util.List;

public interface IReadWrite<T> {
    void writeToFile(List<T> items, String filePath);
    List<T> readFromFile(String filePath);
}
