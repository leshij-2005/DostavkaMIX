package ru.dostavkamix.denis.dostavkamix.model.account;

/**
 * Created by Денис on 04.09.2016.
 */

public class Credentials {

    public Credentials(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }
}
