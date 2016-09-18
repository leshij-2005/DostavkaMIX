package ru.dostavkamix.denis.dostavkamix.model.order;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Buyer;
import rx.Observable;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class OrderManager {

    OrderService orderService;

    public OrderManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chaihanamix.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        orderService = retrofit.create(OrderService.class);
    }

    public Observable sendOrder(Buyer buyer) {
        return orderService.sendOrder(buyer);
    }
}
