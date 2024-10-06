
import model.*;
import java.util.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
        RentalSystem rentalSystem = new RentalSystem();

        // Adding cars to the system
        rentalSystem.addCar(new Car("Toyota", "Glanza", 2020, "ABCD1234", 600, true));
        rentalSystem.addCar(new Car("Honda", "Civic", 2021, "DEFG5678", 700, true));
        rentalSystem.addCar(new Car("Ford", "Mustang", 2023, "HIJK9823", 1200, true));

        // Show main menu
        rentalSystem.showMenu();
    }
}
