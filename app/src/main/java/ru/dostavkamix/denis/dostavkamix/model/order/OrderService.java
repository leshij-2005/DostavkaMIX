package ru.dostavkamix.denis.dostavkamix.model.order;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.OrderResponse;
import rx.Observable;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface OrderService {

    @POST("server/order")
    Observable sendOrder(@Body Order order);

    @POST("server/order")
    Observable<OrderResponse> sendOrder(@Body Order order, @Header("Authorization") String access_token);

    @GET("api/v1/order")
    Observable<List<Order>> getOrders(@Header("Authorization") String access_token);
}
