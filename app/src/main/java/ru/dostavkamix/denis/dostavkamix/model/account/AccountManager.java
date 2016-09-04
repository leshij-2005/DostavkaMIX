package ru.dostavkamix.denis.dostavkamix.model.account;


import rx.Observable;

/**
 * Created by Денис on 04.09.2016.
 */

public interface AccountManager {

    Observable<Credentials> doSignUp(AuthCredentials authCredentials);

    Observable<Credentials> doSignIn(AuthCredentials authCredentials);

    Observable<Account> getAccount(Credentials credentials);

    Observable<Account> updateAccount(Account account);
}
