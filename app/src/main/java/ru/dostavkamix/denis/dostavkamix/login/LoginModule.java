package ru.dostavkamix.denis.dostavkamix.login;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

@Module
public class LoginModule {

    @Provides
    @Singleton
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }
}
