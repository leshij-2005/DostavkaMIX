package ru.dostavkamix.denis.dostavkamix.model.order;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Buyer;
import rx.Observable;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface OrderService {

    @POST("server/order/")
    Observable sendOrder(@Body Buyer buyer);

    @POST("server/order/")
    Observable sendOrder(@Body Buyer buyer, @Header("Authorization") String access_token);
}
