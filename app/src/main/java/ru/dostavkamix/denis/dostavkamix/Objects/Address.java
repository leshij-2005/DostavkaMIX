package ru.dostavkamix.denis.dostavkamix.Objects;

/**
 * Created by den on 22.08.16.
 * <p>
 * Special for Android School GDG
 */

public class Address {

    private String street = "";
    private String number = "";
    private String porch = "";
    private String floor = "";
    private String apartment = "";

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPorch(String porch) {
        this.porch = porch;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPorch() {
        return porch;
    }

    public String getFloor() {
        return floor;
    }

    public String getApartment() {
        return apartment;
    }

    public boolean isEmpty() {
        return street == "";
    }
}
