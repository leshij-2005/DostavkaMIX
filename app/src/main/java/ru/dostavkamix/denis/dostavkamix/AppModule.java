package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.dostavkamix.denis.dostavkamix.model.preference.AndroidPreferenceManager;
import ru.dostavkamix.denis.dostavkamix.model.preference.PreferenceManager;

/**
 * Created by denis on 27.09.16.
 *
 * @author Denis Tkachenko
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    PreferenceManager providePreferenceManager() {
        return new AndroidPreferenceManager(context);
    }
}
