package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.Account.Address;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

class EditPresenter extends MvpBasePresenter<EditView> {

    @Inject
    AccountManager accountManager;
    Account account;

    EditPresenter() {
        AppController.inject(this);
        account = new Account(accountManager.getCurrentAccount());
    }

    Account getUser() {return account;}

    void updateUser(Account account) {
        accountManager.updateAccount(accountManager.getCurrentAuth(), this.account);
    }

    void addAddress(Address address) {
        accountManager.getCurrentAccount().getAddresses().add(address);
    }
}
