package ru.dostavkamix.denis.dostavkamix.login;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.AuthCredentials;
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

    private Pattern pattern;

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
    };

    private Action1<Object> nextAction = object -> Log.d(TAG, "next: ");

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public LoginPresenter() {
        AppController.inject(this);
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public void doSignin(AuthCredentials authCredentials) {
        if(isViewAttached()) getView().showLoading();

        cancelSubscription();

        subscription = accountManager.doSignIn(authCredentials)
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

    boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
