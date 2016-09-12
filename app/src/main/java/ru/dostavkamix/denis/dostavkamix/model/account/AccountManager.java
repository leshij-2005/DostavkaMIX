package ru.dostavkamix.denis.dostavkamix.model.account;


import rx.Observable;

/**
 * Created by Денис on 04.09.2016.
 */

public interface AccountManager {

    Observable<Credentials> doSignUp(AuthCredentials authCredentials, Account account);

    Observable<Credentials> doSignIn(AuthCredentials authCredentials);

    Observable<Account> getAccount(Credentials credentials);

    Observable<Account> updateAccount(Credentials credentials, Account account);

    Credentials getCurrentAuth();

    Account getCurrentAccount();

    boolean isUserAuthenticated();

    boolean isUserGetted();

    void setCurrentCredentials(Credentials credentials);

    void doLogout();
}
