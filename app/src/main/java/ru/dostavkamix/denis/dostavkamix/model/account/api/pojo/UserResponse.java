package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Денис on 04.09.2016.
 */

public class UserResponse extends BaseResponse {

    @SerializedName("response")
    private User user;

    public User getUser() {
        return user;
    }
}
