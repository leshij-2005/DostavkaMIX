package ru.dostavkamix.denis.dostavkamix.model.account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Денис on 04.09.2016.
 */

public class Account {

    private String name;
    private String phone;
    private String email;
    private String birthday;
    private int points;
    private String password;
    private List<Address> addresses = new ArrayList<>();

    public Account(String name, String phone, String email, String birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
    }

    public Account(String name, String phone, String email, String birthday, List<Address> addresses) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.addresses = addresses;
    }

    public Account(String name, String phone, String email, String birthday, List<Address> addresses, int points) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.addresses = addresses;
        this.points = points;
    }

    public Account(Account account) {
        name = account.getName();
        phone = account.getPhone();
        email = account.getEmail();
        birthday = account.getBirthday();
        points = account.getPoints();
        password = account.getPassword();
        addresses = account.getAddresses();
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Address> getAddresses() {

        return addresses;
    }

    public static class Address {

        private String street;
        private String number;
        private String apartment;

        public void setStreet(String street) {
            this.street = street;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getApartment() {
            return apartment;
        }

        public Address(String street, String number, String apartment) {

            this.street = street;
            this.number = number;
            this.apartment = apartment;
        }

        public Address() {
        }
    }
}
