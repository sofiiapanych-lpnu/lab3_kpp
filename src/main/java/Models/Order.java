package Models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderID;
    private Client client;
    private transient List<Dish> dishes; //transient щоб не було серіалізоване

    public Order(int orderID, Client client, List<Dish> dishes) {
        this.orderID = orderID;
        this.client = client;
        this.dishes = dishes;
    }
    public Order() {}
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderNumber) {
        this.orderID = orderNumber;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public List<Dish> getDishes() {
        return dishes;
    }
    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
    @Override
    public String toString() {
        return "{" + "orderNumber=" + orderID + ", client=" + client + ", dishes=" + dishes + '}';
    }
    public Order cloneOrderWithoutClients(){
        return new Order(this.orderID, null, this.dishes);
    }
}
