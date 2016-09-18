package ru.dostavkamix.denis.dostavkamix.model.account;


import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Денис on 04.09.2016.
 */

public interface AccountManager {

    Observable<Credentials> doSignUp(AuthCredentials authCredentials, Account account);

    Observable<Credentials> doSignIn(AuthCredentials authCredentials);

    Observable<Account> getAccount(Credentials credentials);

    Observable<Account> getAccount();

    Observable<Account> updateAccount(Credentials credentials, Account account);

    Observable<Account> updateAccount(Account account);

    Credentials getCurrentAuth();

    Account getCurrentAccount();

    boolean isUserAuthenticated();

    boolean isUserGetted();

    void setCurrentCredentials(Credentials credentials);

    void setCurrentAccount(Account account);

    void doLogout();

    PublishSubject<Account> getSubjectAccount();

    PublishSubject<Credentials> getSubjectCredentials();
}
