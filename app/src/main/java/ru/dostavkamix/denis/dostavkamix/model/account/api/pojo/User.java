package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Денис on 04.09.2016.
 */

public class User {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("phone")
    String phone;
    @SerializedName("birthday")
    String birthday;
    @SerializedName("points")
    int points;
    @SerializedName("addresses")
    List<Address> addresses = new ArrayList<>();
    @SerializedName("password")
    String password;
    @SerializedName("user")
    User user;

    public User(String name, String email, String phone, String birthday, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getPoints() {
        return points;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getPassword() {
        return password;
    }

    public class Addresses {

    }

    public class Address {

        @SerializedName("street")
        private String street;
        @SerializedName("number")
        private String number;
        @SerializedName("porch")
        private String porch;
        @SerializedName("floor")
        private String floor;
        @SerializedName("apartment")
        private String apartment;

        /**
         *
         * @return
         * The street
         */
        public String getStreet() {
            return street;
        }

        /**
         *
         * @param street
         * The street
         */
        public void setStreet(String street) {
            this.street = street;
        }

        /**
         *
         * @return
         * The number
         */
        public String getNumber() {
            return number;
        }

        /**
         *
         * @param number
         * The number
         */
        public void setNumber(String number) {
            this.number = number;
        }

        /**
         *
         * @return
         * The porch
         */
        public String getPorch() {
            return porch;
        }

        /**
         *
         * @param porch
         * The porch
         */
        public void setPorch(String porch) {
            this.porch = porch;
        }

        /**
         *
         * @return
         * The floor
         */
        public String getFloor() {
            return floor;
        }

        /**
         *
         * @param floor
         * The floor
         */
        public void setFloor(String floor) {
            this.floor = floor;
        }

        /**
         *
         * @return
         * The apartment
         */
        public String getApartment() {
            return apartment;
        }

        /**
         *
         * @param apartment
         * The apartment
         */
        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

    }
}
