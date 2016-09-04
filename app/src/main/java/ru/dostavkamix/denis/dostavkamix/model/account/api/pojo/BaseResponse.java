package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Денис on 04.09.2016.
 */

public class BaseResponse {

    @SerializedName("status")
    boolean status = true;
    @SerializedName("msg")
    String msg;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {

        return status;
    }

    public String getMsg() {
        return msg;
    }
}
