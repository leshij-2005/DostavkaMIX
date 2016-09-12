package ru.dostavkamix.denis.dostavkamix.login;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface LoginView extends MvpView {

    void showSigninForm();

    void showSignupForm();

    void showIntro();

    void showLoading();

    void showError(@Nullable Throwable throwable);

    void loginSuccessful();
}
