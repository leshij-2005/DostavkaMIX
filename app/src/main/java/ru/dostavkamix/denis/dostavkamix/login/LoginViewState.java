package ru.dostavkamix.denis.dostavkamix.login;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public class LoginViewState implements ViewState<LoginView> {

    static final int STATE_SHOW_SIGNIN_FORM = 0;
    static final int STATE_SHOW_SIGNUP_FORM = 1;
    static final int STATE_SHOW_INTRO = 2;
    static final int STATE_SHOW_LOADING = 3;
    static final int STATE_SHOW_ERROR = 4;

    int state = STATE_SHOW_SIGNIN_FORM;

    Throwable throwable;

    @Override public void apply(LoginView view, boolean retained) {

        switch (state) {
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;

            case STATE_SHOW_ERROR:
                view.showError(null);
                break;

            case STATE_SHOW_SIGNIN_FORM:
                view.showSigninForm();
                break;
            case STATE_SHOW_SIGNUP_FORM:
                view.showSignupForm();
                break;
            case STATE_SHOW_INTRO:
                view.showIntro();
                break;
        }
    }

    void setShowSigninForm() {
        state = STATE_SHOW_SIGNIN_FORM;
    }

    void setShowSignupForm() {
        state = STATE_SHOW_SIGNUP_FORM;
    }

    void setShowIntro() {
        state = STATE_SHOW_INTRO;
    }

    void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }
}
