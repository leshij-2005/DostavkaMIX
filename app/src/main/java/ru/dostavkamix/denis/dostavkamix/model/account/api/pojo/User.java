package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

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
    String addresses;
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

    public String getAddresses() {
        return addresses;
    }

    public String getPassword() {
        return password;
    }

    public class Addresses {

    }
}
