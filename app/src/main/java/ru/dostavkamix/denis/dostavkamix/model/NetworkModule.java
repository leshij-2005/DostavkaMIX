package ru.dostavkamix.denis.dostavkamix.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.dostavkamix.denis.dostavkamix.Custom.LruBitmapCache;

/**
 * Created by denis on 26.09.16.
 *
 * @author Denis Tkachenko
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @NonNull
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    @NonNull
    ImageLoader provideImageLoader(Context context) {
        return new ImageLoader(
                Volley.newRequestQueue(context),
                new LruBitmapCache());
    }
}
