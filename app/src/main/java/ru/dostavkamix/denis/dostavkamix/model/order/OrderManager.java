package ru.dostavkamix.denis.dostavkamix.model.order;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import rx.Observable;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class OrderManager {

    private OrderService orderService;
    @Inject
    OkHttpClient httpClient;

    public OrderManager() {
        AppController.getComponent().inject(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.chaihanamix.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build();

        orderService = retrofit.create(OrderService.class);


    }

    public Observable sendOrder(Order order) {
        return orderService.sendOrder(order);
    }

    public Observable<String> sendOrder(Order order, String token) {
        return orderService.sendOrder(order, token)
                .map(String::valueOf);
    }


}
