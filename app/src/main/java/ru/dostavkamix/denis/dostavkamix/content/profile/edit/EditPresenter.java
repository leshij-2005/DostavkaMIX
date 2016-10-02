package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.base.BaseRxLcePresenter;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import ru.dostavkamix.denis.dostavkamix.model.account.Credentials;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Token;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class EditPresenter extends BaseRxLcePresenter<EditView, Account> {

    @Inject
    AccountManager accountManager;

    EditPresenter() {
        AppController.getComponent().inject(this);
    }

    void updateAccount(Account account) {
        subscribe(accountManager.updateAccount(account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()), true);
    }

    void loadAccount() {
        subscribe(accountManager.getAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()), false);
    }
}
