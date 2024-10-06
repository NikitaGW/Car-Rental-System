package model;

import java.time.LocalDate;


public class Reservation {
    private String reservationId;
    private Car car;
    private Customer Customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    public Reservation(String reservationId,Car car, Customer Customer, LocalDate startDate, LocalDate endDate, double toatlPrice){
        this.reservationId=reservationId;
        this.car=car;
        this.Customer=Customer;
        this.startDate=startDate;
        this.endDate=endDate;
        this.totalPrice=toatlPrice;
    }
    public String getReservationId(){
        return this.reservationId;
    }

    public Car getCar(){
        return this.car;
    }

    public Customer getCustomer(){
        return this.Customer;
    }

    public LocalDate getStartDate(){
        return this.startDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    }

