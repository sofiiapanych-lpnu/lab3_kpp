package Models;
import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Client(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    public Client() {}
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}
