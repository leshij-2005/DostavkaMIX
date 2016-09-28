package ru.dostavkamix.denis.dostavkamix.model;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.api.ChaihanaAccountManager;
import ru.dostavkamix.denis.dostavkamix.model.order.OrderManager;
import ru.dostavkamix.denis.dostavkamix.model.preference.PreferenceManager;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

@Module
public class ModelModule {

    @Provides
    @Singleton
    AccountManager provideAccountManager(OkHttpClient httpClient, PreferenceManager preferenceManager) {
        return new ChaihanaAccountManager(httpClient, preferenceManager);
    }

    @Provides
    @Singleton
    OrderManager provideOrderManager() {
        return new OrderManager();
    }
}
