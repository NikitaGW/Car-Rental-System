
package model;

import model.Payment.PayPalPaymentProcessor;
import model.Payment.CreditCardPaymentProcessor;
import model.Payment.PaymentProcessor;
import model.*;
import java.util.*;
import java.time.LocalDate;

public class RentalSystem {
    private Map<String, Car> cars;
    private Map<String, Reservation> reservations;
    private PaymentProcessor paymentProcessor;

    public RentalSystem() {
        cars = new HashMap<>();
        reservations = new HashMap<>();
    }

    // Menu loop for interacting with the user
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Welcome to XYZ Car Rental System ===");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Exit");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableCars();
                    break;
                case 2:
                    rentCar(scanner);
                    break;
                case 3:
                    returnCar(scanner);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using RentX Car Rental System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    public void addCar(Car car) {
        cars.put(car.getLicensePlate(), car);
    }

    public void viewAvailableCars() {
        System.out.println("\nAvailable Cars:");
        for (Car car : cars.values()) {
            if (car.getIsAvailable()) {
                System.out.println(
                        car.getMaker() + " " + car.getModel() + " - Rs. " + car.getRentalPricePerDay() + " per day");
            }
        }
    }

    public void rentCar(Scanner scanner) {
        scanner.nextLine(); // Consume leftover newline
        System.out.print("Enter car maker: ");
        String maker = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3); // Example dates

        List<Car> availableCars = searchCars(maker, model, startDate, endDate);
        if (availableCars.isEmpty()) {
            System.out.println("No cars available for the given specifications.");
            return;
        }

        Car selectedCar = availableCars.get(0); // Automatically select the first available car
        Customer customer = new Customer("Rohit", "9768797876", "DL1234");
        Reservation reservation = makeReservation(customer, selectedCar, startDate, endDate);

        if (reservation != null) {
            System.out.println(
                    "Reservation successful for car: " + selectedCar.getMaker() + " " + selectedCar.getModel());
            System.out.print("Choose payment method (1. Credit Card, 2. PayPal): ");
            int paymentOption = scanner.nextInt();

            if (paymentOption == 1) {
                paymentProcessor = new CreditCardPaymentProcessor();
            } else if (paymentOption == 2) {
                paymentProcessor = new PayPalPaymentProcessor();
            }

            if (processPayment(reservation)) {
                System.out.println("Payment successful. Reservation ID: " + reservation.getReservationId());
            } else {
                System.out.println("Payment failed, reservation cancelled.");
                cancelReservation(reservation.getReservationId());
            }
        } else {
            System.out.println("Car not available for the selected dates.");
        }
    }

    public void returnCar(Scanner scanner) {
        scanner.nextLine(); // Consume leftover newline
        System.out.print("Enter reservation ID: ");
        String reservationId = scanner.nextLine();
        cancelReservation(reservationId);
        System.out.println("Car returned successfully.");
    }

    public List<Car> searchCars(String maker, String model, LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getMaker().equals(maker) && car.getModel().equals(model) && car.getIsAvailable()) {
                if (isCarAvailable(car, startDate, endDate)) {
                    availableCars.add(car);
                }
            }
        }
        return availableCars;
    }

    private boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCar().equals(car)) {
                if (startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Reservation makeReservation(Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
        if (isCarAvailable(car, startDate, endDate)) {
            String reservationId = generateReservationId();
            Reservation reservation = new Reservation(reservationId, car, customer, startDate, endDate,
                    car.getRentalPricePerDay() * 3);
            reservations.put(reservationId, reservation);
            car.setAvailable(false);
            return reservation;
        }
        return null;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservations.remove(reservationId);
        if (reservation != null) {
            reservation.getCar().setAvailable(true);
        }
    }

    private String generateReservationId() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public boolean processPayment(Reservation reservation) {
        return paymentProcessor.processPayment(reservation.getTotalPrice());
    }
}
