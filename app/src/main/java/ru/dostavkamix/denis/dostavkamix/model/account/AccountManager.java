package ru.dostavkamix.denis.dostavkamix.model.account;


import java.util.List;

import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Transaction;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Денис on 04.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface AccountManager {

    Observable<Credentials> doSignUp(AuthCredentials authCredentials, Account account);

    Observable<Credentials> doSignIn(AuthCredentials authCredentials);

    Observable<Account> getAccount(Credentials credentials);

    Observable<Account> getAccount();

    Observable<List<Order>> getOrders(Credentials credentials);

    Observable<List<Order>> getOrders();

    Observable<List<Transaction>> getTransactions(Credentials credentials);

    Observable<List<Transaction>> getTransactions();

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
}
