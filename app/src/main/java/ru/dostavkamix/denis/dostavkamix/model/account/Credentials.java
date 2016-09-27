package ru.dostavkamix.denis.dostavkamix.model.account;

/**
 * Created by Денис on 04.09.2016.
 */

public class Credentials {

    public Credentials(String token, String user_id) {
        this.token = token;
        this.user_id = user_id;
    }

    private String token;
    private String user_id;

    public String getUser_id() { return user_id; }

    public String getToken() {
        return token;
    }
}
