package model;

public class Car {
    private String maker;
    private String model;
    private int year;
    private String licensePlate;
    private int rentalPricePerDay;
    private boolean isAvailable;

    // Constructor
    public Car(String maker, String model, int year, String licensePlate, int rentalPricePerDay,boolean isAvailable){
        this.maker=maker;
        this.model=model;
        this.year=year;
        this.licensePlate=licensePlate;
        this.rentalPricePerDay=rentalPricePerDay;
        this.isAvailable=isAvailable;
    }

    public String getMaker(){
        return this.maker;
    }
    public String getModel(){
        return this.model;
    }

    public int getYear(){
        return this.year;
    }

    public String getLicensePlate(){
        return this.licensePlate;
    }

    public int getRentalPricePerDay(){
        return this.rentalPricePerDay;
    }

    public boolean getIsAvailable(){
        return this.isAvailable;
    }
    
    public void setAvailable(boolean isAvailable){
        this.isAvailable=isAvailable;
    }
    }



