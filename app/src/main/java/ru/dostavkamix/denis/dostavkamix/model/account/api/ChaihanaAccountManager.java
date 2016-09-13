package ru.dostavkamix.denis.dostavkamix.model.account.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.AuthCredentials;
import ru.dostavkamix.denis.dostavkamix.model.account.Credentials;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.User;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.User_;
import rx.Observable;

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

    public ChaihanaAccountManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AccountAPIService.class);
    }

    @Override
    public Observable<Credentials> doSignUp(AuthCredentials authCredentials, Account account) {
        return service.createUser(new User(
                account.getName(),
                authCredentials.getEmail(),
                account.getPhone(),
                account.getBirthday(),
                authCredentials.getPassword()))
                .compose(new ResponseTransformer<>())
                .doOnNext(userResponse ->
                        currentAccount = Utils.UserResponse2Account(userResponse))
                .flatMap(userResponse -> service.getToken(new ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.AuthCredentials(authCredentials.getEmail(), authCredentials.getPassword(), "123456")))
                .map(token -> new Credentials(token.getAccess_token()))
                .doOnNext(credentials -> currentAuth = credentials);
    }

    @Override
    public Observable<Credentials> doSignIn(AuthCredentials authCredentials) {
        return service.getToken(new ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.AuthCredentials(authCredentials.getEmail(), authCredentials.getPassword(), "123456"))
                .compose(new ResponseTransformer<>())
                .map(token -> new Credentials(token.getAccess_token()));
    }

    @Override
    public Observable<Account> getAccount(Credentials credentials) {
        return service.getUser(credentials.getToken())
                .map(User_::getUser)
                .map(Utils::User2Account);
    }

    @Override
    public Observable<Account> updateAccount(Credentials credentials, Account account) {
        return service.updateUser(credentials.getToken(), Utils.Account2User(account))
                .map(Utils::UserResponse2Account);
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
        currentAuth = credentials;
    }

    @Override
    public void doLogout() {
        currentAccount = null;
        currentAuth = null;
    }

}
