package ru.dostavkamix.denis.dostavkamix.content.profile.orders;

import java.util.List;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.base.BaseRxLcePresenter;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class OrdersPresenter extends BaseRxLcePresenter<OrdersView, List<Order>> {

    @Inject
    AccountManager accountManager;

    public OrdersPresenter() {
        AppController.getComponent().inject(this);
    }

    void loadOrders() {
        subscribe(accountManager.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()), false);
    }
}
