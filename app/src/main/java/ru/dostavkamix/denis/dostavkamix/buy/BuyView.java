package ru.dostavkamix.denis.dostavkamix.buy;

import com.hannesdorfmann.mosby.mvp.MvpView;

import ru.dostavkamix.denis.dostavkamix.model.account.Account;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface BuyView extends MvpView {

    void showAccount(Account account);

    void showSuccess();

    void showBuying();

    void showError(Throwable throwable);
}
