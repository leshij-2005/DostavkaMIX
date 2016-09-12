package ru.dostavkamix.denis.dostavkamix.model.account;

/**
 * Created by Денис on 04.09.2016.
 */

public class AuthCredentials {

    private String email;
    private String password;

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
