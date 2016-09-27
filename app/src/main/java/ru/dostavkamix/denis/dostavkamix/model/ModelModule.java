package ru.dostavkamix.denis.dostavkamix.model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.api.ChaihanaAccountManager;
import ru.dostavkamix.denis.dostavkamix.model.order.OrderManager;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

@Module
public class ModelModule {

    @Provides
    @Singleton
    AccountManager provideAccountManager() {
        return new ChaihanaAccountManager();
    }

    @Provides
    @Singleton
    OrderManager provideOrderManager() {
        return new OrderManager();
    }
}
