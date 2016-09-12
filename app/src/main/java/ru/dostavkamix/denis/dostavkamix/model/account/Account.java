package ru.dostavkamix.denis.dostavkamix.model.account;

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

    public Account(String name, String phone, String email, String birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
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
}
