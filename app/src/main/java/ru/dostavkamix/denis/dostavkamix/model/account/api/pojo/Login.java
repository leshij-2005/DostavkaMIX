package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Денис on 04.09.2016.
 */

public class Login {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("app_key")
    String app_key;

    public Login(String email, String password, String app_key) {
        this.email = email;
        this.password = password;
        this.app_key = app_key;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getEmail() {

        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getApp_key() {
        return app_key;
    }
}
