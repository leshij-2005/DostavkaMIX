package ru.dostavkamix.denis.dostavkamix.model.order.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by denis on 27.09.16.
 *
 * @author Denis Tkachenko
 */

public class OrderResponse {

    @SerializedName("orderId")
    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
