package ru.dostavkamix.denis.dostavkamix.model.account.api;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Login;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.User;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.UserResponse;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Token;

import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import rx.Observable;

/**
 * Created by Денис on 04.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface AccountAPIService {

    @POST("api/v1/user")
    Observable<UserResponse> createUser(@Body User user);

    @PUT("api/v1/user")
    Observable<UserResponse> updateUser(@Header("Authorization") String access_token, @Body User user);

    @GET("api/v1/user")
    Observable<User> getUser(@Header("Authorization") String access_token);

    @POST("api/v1/token")
    Observable<Token> getToken(@Body Login login);

    @GET("api/v1/order")
    Observable<List<Order>> getOrders(@Header("Authorization") String access_token);

}
