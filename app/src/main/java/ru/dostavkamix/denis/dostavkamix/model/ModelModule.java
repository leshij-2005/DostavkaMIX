package ru.dostavkamix.denis.dostavkamix.model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.dostavkamix.denis.dostavkamix.login.LoginPresenter;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.api.ChaihanaAccountManager;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

@Module(injects = LoginPresenter.class)
public class ModelModule {

    @Provides
    @Singleton
    AccountManager provideAccountManager() {
        return new ChaihanaAccountManager();
    }
}
