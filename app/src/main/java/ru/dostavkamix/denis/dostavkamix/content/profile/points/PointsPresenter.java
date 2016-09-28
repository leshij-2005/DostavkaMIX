package ru.dostavkamix.denis.dostavkamix.content.profile.points;

import java.util.List;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.base.BaseRxLcePresenter;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Transaction;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class PointsPresenter extends BaseRxLcePresenter<PointsView, List<Transaction>> {

    @Inject
    AccountManager accountManager;

    public PointsPresenter() {
        AppController.getComponent().inject(this);
    }

    void loadTransactions() {
        subscribe(accountManager.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()), false);
    }
}
