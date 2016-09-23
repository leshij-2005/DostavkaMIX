package ru.dostavkamix.denis.dostavkamix.buy;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.order.OrderManager;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Buyer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class BuyPresenter extends MvpBasePresenter<BuyView> {

    @Inject OrderManager orderManager;
    @Inject AccountManager accountManager;

    private Action1 success = o -> {
        if(isViewAttached()) getView().showSuccess();
    };
    private Action1<Throwable> error = throwable -> {
        if(isViewAttached()) getView().showError();
    };

    private Observable test = Observable.just("")
            .delay(1, TimeUnit.SECONDS);

    public BuyPresenter() {
        AppController.inject(this);
    }

    void buying(Buyer buyer) {
        if(isViewAttached()) getView().showBuying();
        test.subscribe(success, error);
    }

    void loadAccount() {
        if(accountManager.isUserGetted()) if (isViewAttached()) getView().showAccount(accountManager.getCurrentAccount());
        else if(accountManager.isUserAuthenticated()) accountManager.getAccount()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(account -> {
            if(isViewAttached()) getView().showAccount(account);
        });
    }

}
