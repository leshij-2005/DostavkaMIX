package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Денис on 04.09.2016.
 */

public class Token extends BaseResponse {

    @SerializedName("user_id")
    String user_id;
    @SerializedName("access_token")
    String access_token;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {

        return user_id;
    }

    public String getAccess_token() {
        return access_token;
    }
}
