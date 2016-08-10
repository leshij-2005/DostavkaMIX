package ru.dostavkamix.denis.dostavkamix.Objects;

/**
 * Created by Денис on 02.08.2016.
 */

public class User {

    private String token;
    private String name;
    private String email;
    private String created_at;
    private String update_at;
    private String birthday;
    private String phone;
    private int points;
    private String addresses;
    private String pass;

    public User(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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

    public String getUpdate_at() {
        return update_at;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoints() {
        return points;
    }

    public String getAddresses() {
        return addresses;
    }

    public String getPass() {
        return pass;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
