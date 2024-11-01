import Initializers.DefaultDataInitializer;
import Models.Order;
import Services.DataManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = DefaultDataInitializer.getDefaultOrders();
        DataManager dataManager = new DataManager();
        dataManager.manageClients();
        dataManager.manageDishes();
        dataManager.manageOrders(orders);
    }
}
