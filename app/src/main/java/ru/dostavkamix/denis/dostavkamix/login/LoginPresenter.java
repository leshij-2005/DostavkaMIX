package ru.dostavkamix.denis.dostavkamix.login;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.AuthCredentials;
import ru.dostavkamix.denis.dostavkamix.model.account.Credentials;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private static String TAG = "LoginPreseter";

    @Inject
    AccountManager accountManager;

    private Subscription subscription;

    private Action0 completedAction = () -> {
        if (isViewAttached()) {
            getView().loginSuccessful();
        }

        Log.d(TAG, "complete: ");
    };

    private Action1<Throwable> errorAction = throwable -> {
        if (isViewAttached()) {
            getView().showError(throwable);
        }

        Log.d(TAG, "error: " + throwable);
        throwable.printStackTrace();
    };

    private Action1<Object> nextAction = object -> Log.d(TAG, "next: ");

    public LoginPresenter() {
        AppController.getComponent().inject(this);
    }

    public void doSignin(AuthCredentials authCredentials) {
        if(isViewAttached()) getView().showLoading();

        cancelSubscription();

        subscription = accountManager.doSignIn(authCredentials)
                .doOnNext(credentials -> Log.d(TAG, "doSignin: token: " + credentials.getToken()))
                .flatMap(credentials -> accountManager.getAccount(credentials))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextAction, errorAction, completedAction);
    }

    public void doSignup(AuthCredentials authCredentials, Account account) {
        if(isViewAttached()) getView().showLoading();

        cancelSubscription();

        subscription = accountManager.doSignUp(authCredentials, account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nextAction, errorAction, completedAction);
    }

    /**
     * Cancels any previous callback
     */
    private void cancelSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    Credentials getToken() {
        return accountManager.getCurrentAuth();
    }
}
