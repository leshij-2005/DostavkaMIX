package ru.dostavkamix.denis.dostavkamix.buy;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.order.OrderManager;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
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

    private Action1<String> success = o -> {
        if(isViewAttached()) getView().showSuccess();
    };
    private Action1<Throwable> error = throwable -> {
        if(isViewAttached()) getView().showError(throwable);
    };

    private Observable test = Observable.just("")
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    public BuyPresenter() {
        AppController.getComponent().inject(this);
    }

    void buying(Order order) {
        if(accountManager.isUserAuthenticated()) order.setUser_id(accountManager.getCurrentAuth().getUser_id());
        if(isViewAttached()) getView().showBuying();
        if(accountManager.isUserAuthenticated()) {
            orderManager.sendOrder(order, accountManager.getCurrentAuth().getToken())
                    .delay(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success, error);
        }
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

    boolean validOrderTime(int hourOfDay, int minute) {
        return !((hourOfDay < 12 && ((hourOfDay == 0 && minute > 30) || hourOfDay > 0)));
    }

}
