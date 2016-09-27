package ru.dostavkamix.denis.dostavkamix.model.account.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.AuthCredentials;
import ru.dostavkamix.denis.dostavkamix.model.account.Credentials;
import ru.dostavkamix.denis.dostavkamix.model.account.NotAuthenticatedException;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Address;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Login;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.User;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

public class ChaihanaAccountManager implements AccountManager {

    private static final String BASE_URL = "http://cabinet.chaihanamix.ru/";
    private AccountAPIService service;

    private Account currentAccount;
    private Credentials currentAuth;

    PublishSubject<Account> subjectAccount = PublishSubject.create();
    PublishSubject<Credentials> subjectCredentials = PublishSubject.create();

    @Inject
    OkHttpClient httpClient;

    public ChaihanaAccountManager() {
        AppController.getComponent().inject(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        service = retrofit.create(AccountAPIService.class);

        subjectAccount.subscribe(this::setCurrentAccount);
        subjectCredentials.subscribe(this::setCurrentCredentials);
    }

    @Override
    public Observable<Credentials> doSignUp(AuthCredentials authCredentials, Account account) {
        return service.createUser(new User(
                account.getName(),
                authCredentials.getEmail(),
                account.getPhone(),
                account.getBirthday(),
                authCredentials.getPassword(),
                new ArrayList<>()))
                .compose(new ResponseTransformer<>())
                .doOnNext(userResponse ->
                        currentAccount = Utils.User2Account(userResponse.getUser()))
                .flatMap(userResponse -> service.getToken(new Login(authCredentials.getEmail(), authCredentials.getPassword(), "123456")))
                .map(token -> new Credentials(token.getAccess_token(), token.getUser_id()))
                .doOnNext(subjectCredentials::onNext);
    }

    @Override
    public Observable<Credentials> doSignIn(AuthCredentials authCredentials) {
        return service.getToken(new Login(authCredentials.getEmail(), authCredentials.getPassword(), "123456"))
                .compose(new ResponseTransformer<>())
                .map(token -> new Credentials(token.getAccess_token(), token.getUser_id()))
                .doOnNext(subjectCredentials::onNext);
    }

    @Override
    public Observable<Account> getAccount(Credentials credentials) {

        return service.getUser(credentials.getToken())
                .map(User::getUser)
                .map(Utils::User2Account)
                .doOnNext(subjectAccount::onNext);
    }

    @Override
    public Observable<Account> getAccount() {
        if(!isUserAuthenticated()) {
            return Observable.error(new NotAuthenticatedException());
        }

        return getAccount(getCurrentAuth());
    }

    @Override
    public Observable<List<Order>> getOrders(Credentials credentials) {
        return service.getOrders(credentials.getToken());
    }

    @Override
    public Observable<List<Order>> getOrders() {
        if(!isUserAuthenticated()) {
            return Observable.error(new NotAuthenticatedException());
        }

        return getOrders(currentAuth);
    }

    @Override
    public Observable<Account> updateAccount(Credentials credentials, Account account) {
        return service.updateUser(credentials.getToken(), Utils.Account2User(account))
                .flatMap(userResponse -> {
                    if(!userResponse.isStatus()) return Observable.error(new AccountException(userResponse));
                    else return Observable.just(Utils.User2Account(userResponse.getUser()));
                })
                .doOnNext(subjectAccount::onNext);
    }

    @Override
    public Observable<Account> updateAccount(Account account) {
        if(!isUserAuthenticated()) {
            return Observable.error(new NotAuthenticatedException());
        }

        return updateAccount(currentAuth, account);
    }

    @Override
    public Credentials getCurrentAuth() {
        return currentAuth;
    }

    @Override
    public Account getCurrentAccount() {
        return currentAccount;
    }

    @Override
    public boolean isUserAuthenticated() {
        return currentAuth != null;
    }

    @Override
    public boolean isUserGetted() {
        return currentAccount != null;
    }

    @Override
    public void setCurrentCredentials(Credentials credentials) {
        this.currentAuth = credentials;
    }

    @Override
    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    @Override
    public void doLogout() {
        currentAccount = null;
        currentAuth = null;
    }

    @Override
    public PublishSubject<Account> getSubjectAccount() {
        return subjectAccount;
    }

    @Override
    public PublishSubject<Credentials> getSubjectCredentials() {
        return subjectCredentials;
    }
}
