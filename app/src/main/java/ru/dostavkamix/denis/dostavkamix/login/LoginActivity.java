package ru.dostavkamix.denis.dostavkamix.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hkm.ui.processbutton.iml.ActionProcessButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseViewStateActivity;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.AuthCredentials;
import ru.dostavkamix.denis.dostavkamix.utils.ExceptionUtuls;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public class LoginActivity extends BaseViewStateActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(android.R.id.content) View content;

    @BindView(R.id.intro) View intro;
    @BindView(R.id.sign) View sign;

    @BindView(R.id.label) TextViewPlus label;

    @BindView(R.id.name) EditText name;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.birthday) EditText birthday;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.pass_r) EditText pass_r;

    @BindView(R.id.signup) ActionProcessButton signup;
    @BindView(R.id.signin) ActionProcessButton signin;
    @BindView(R.id.errorView) TextView errorView;
    @BindView(R.id.signSelect) TextView signSelect;

    @Inject LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        BlurBehind.getInstance().setBackground(this);
    }

    @OnClick(R.id.signup)
    void onSignupClick() {
        AuthCredentials auth = new AuthCredentials(email.getText().toString(), pass.getText().toString());
        Account account = new Account(name.getText().toString(), phone.getText().toString(), email.getText().toString(), birthday.getText().toString());

        if(!auth.getPassword().equals(pass_r.getText().toString())) {
            showError(new PassNotMatch());
            return;
        }

        presenter.doSignup(auth, account);
    }

    @OnClick(R.id.signin)
    void onSigninClick() {
        AuthCredentials auth = new AuthCredentials(email.getText().toString(), pass.getText().toString());

        presenter.doSignin(auth);
    }

    @OnClick(R.id.signSelect)
    void onSelectClick() {
        if(((LoginViewState) viewState).state == LoginViewState.STATE_SHOW_SIGNIN_FORM) showSignupForm();
        else showSigninForm();
    }

    @NonNull
    @Override
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showIntro();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return loginPresenter;
    }

    @Override
    public void showSigninForm() {
        ((LoginViewState) viewState).setShowSigninForm();
        setFormEnabled(true);

        intro.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);

        sign.setVisibility(View.VISIBLE);
        signin.setVisibility(View.VISIBLE);

        label.setText(R.string.label_signin);

        name.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        birthday.setVisibility(View.GONE);
        pass.setHint(R.string.hint_password);
        pass_r.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);

        signSelect.setText(R.string.button_signup);
    }

    @Override
    public void showSignupForm() {
        ((LoginViewState) viewState).setShowSignupForm();
        setFormEnabled(true);

        intro.setVisibility(View.GONE);
        signin.setVisibility(View.GONE);

        sign.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);

        label.setText(R.string.label_signup);

        name.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        birthday.setVisibility(View.VISIBLE);
        pass.setHint(R.string.hint_password_new);
        pass_r.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);

        signSelect.setText(R.string.button_signin);
    }

    @Override
    public void showIntro() {
        ((LoginViewState) viewState).setShowIntro();

        sign.setVisibility(View.GONE);

        intro.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        ((LoginViewState) viewState).setShowLoading();

        errorView.setVisibility(View.GONE);
        setFormEnabled(false);
        signin.setProgress(30);
        signup.setProgress(30);
    }

    @Override
    public void showError(@Nullable Throwable throwable) {
        try {
            signin.setProgress(0);
            signup.setProgress(0);

            ExceptionUtuls.ErrorType errorType = ExceptionUtuls.getErrorType(throwable);
            if(errorType == ExceptionUtuls.ErrorType.NAME) shakeView(name);
            if(errorType == ExceptionUtuls.ErrorType.PHONE) shakeView(phone);
            if(errorType == ExceptionUtuls.ErrorType.EMAIL) shakeView(email);
            if(errorType == ExceptionUtuls.ErrorType.BIRTHDAY) shakeView(birthday);
            if(errorType == ExceptionUtuls.ErrorType.PASSWORD) shakeView(pass);
            if(errorType == ExceptionUtuls.ErrorType.PASSWORD_R) shakeView(pass_r);

            errorView.setText(ExceptionUtuls.getMsg(throwable));
            errorView.setVisibility(View.VISIBLE);
            setFormEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loginSuccessful() {
        saveToken(presenter.getToken());

        signin.setProgress(100);
        signup.setProgress(100);
        finish();
    }

    @Override
    protected void injectDependencies() {
        AppController.inject(this);
    }

    private void shakeView(View view) {
        view.clearAnimation();
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }

    private void setFormEnabled(boolean b) {
        name.setEnabled(b);
        phone.setEnabled(b);
        email.setEnabled(b);
        birthday.setEnabled(b);
        pass.setEnabled(b);
        pass_r.setEnabled(b);
    }

    private void saveToken(String token) {
        getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE).edit()
                .putString(getString(R.string.token_key), token)
                .commit();
    }
}
