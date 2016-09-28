package ru.dostavkamix.denis.dostavkamix;

import javax.inject.Singleton;

import dagger.Component;
import ru.dostavkamix.denis.dostavkamix.buy.BuyPresenter;
import ru.dostavkamix.denis.dostavkamix.content.profile.ProfilePresenter;
import ru.dostavkamix.denis.dostavkamix.content.profile.edit.EditPresenter;
import ru.dostavkamix.denis.dostavkamix.content.profile.orders.OrdersAdapter;
import ru.dostavkamix.denis.dostavkamix.content.profile.orders.OrdersPresenter;
import ru.dostavkamix.denis.dostavkamix.content.profile.points.PointsAdapter;
import ru.dostavkamix.denis.dostavkamix.content.profile.points.PointsPresenter;
import ru.dostavkamix.denis.dostavkamix.login.LoginActivity;
import ru.dostavkamix.denis.dostavkamix.login.LoginModule;
import ru.dostavkamix.denis.dostavkamix.login.LoginPresenter;
import ru.dostavkamix.denis.dostavkamix.model.ModelModule;
import ru.dostavkamix.denis.dostavkamix.model.NetworkModule;
import ru.dostavkamix.denis.dostavkamix.model.account.api.ChaihanaAccountManager;
import ru.dostavkamix.denis.dostavkamix.model.content.ContentManager;
import ru.dostavkamix.denis.dostavkamix.model.order.OrderManager;

/**
 * Created by denis on 27.09.16.
 *
 * @author Denis Tkachenko
 */

@Component(modules = { AppModule.class, ModelModule.class, NetworkModule.class, LoginModule.class })
@Singleton
public interface AppComponent {

    void inject(LoginPresenter loginPresenter);
    void inject(ProfilePresenter profilePresenter);
    void inject(EditPresenter editPresenter);
    void inject(BuyPresenter buyPresenter);
    void inject(OrdersPresenter ordersPresenter);

    void inject(ChaihanaAccountManager accountManager);
    void inject(ContentManager contentManager);
    void inject(OrderManager orderManager);
    void inject(OrdersAdapter ordersAdapter );

    void inject(LoginActivity loginActivity);

    void inject(PointsPresenter pointsPresenter);

    void inject(PointsAdapter pointsAdapter);

}
