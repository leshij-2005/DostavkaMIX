package ru.dostavkamix.denis.dostavkamix.content.profile;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AccountManager;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

    @Inject
    AccountManager accountManager;

    public ProfilePresenter() {
        AppController.inject(this);

        accountManager.getSubjectAccount()
                .map(Account::getPoints)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if(getView() != null) getView().updatePointsCount(integer);
                }, Throwable::printStackTrace);
    }
}
