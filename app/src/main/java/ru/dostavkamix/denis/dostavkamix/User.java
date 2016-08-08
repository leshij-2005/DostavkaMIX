package ru.dostavkamix.denis.dostavkamix;

/**
 * Created by Денис on 02.08.2016.
 */

public class User {

    private int id;
    private String token;
    String name;
    String email;
    String created_at;
    String update_at;
    String birthday;
    String phone;
    String points;
    String addresses;
    String pass;

    public User(int id, String token) {
        this.id = id;
        this.token = token;
    }

}
