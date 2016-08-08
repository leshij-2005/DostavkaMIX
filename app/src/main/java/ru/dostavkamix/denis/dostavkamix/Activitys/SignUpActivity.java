package ru.dostavkamix.denis.dostavkamix.Activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.SignCallback;
import ru.dostavkamix.denis.dostavkamix.UserHelper;

/**
 * Created by Денис on 02.08.2016.
 *
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private boolean isSignIn = false;

    private TextViewPlus label;

    private EditText name;
    private EditText email;
    private EditText birthday;
    private EditText pass;
    private EditText pass_r;

    private View signup;
    private View signin;

    private void findUI() {
        label = (TextViewPlus) findViewById(R.id.label);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        birthday = (EditText) findViewById(R.id.birthday);
        pass = (EditText) findViewById(R.id.pass);
        pass_r = (EditText) findViewById(R.id.pass_r);

        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        findUI();
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                Log.d(TAG, "onClick: signup");
                UserHelper.signUp(name.getText().toString(), email.getText().toString(), birthday.getText().toString(), pass.getText().toString(), this, new SignCallback() {
                    @Override
                    public void onSuccess() {
                        signinVisible();
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;
            case R.id.signin:
                Log.d(TAG, "onClick: signin");
                if(isSignIn) {
                    UserHelper.signIn(email.getText().toString(), pass.getText().toString(), this, new SignCallback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
                } else signinVisible();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isSignIn) signupVisible();
        else super.onBackPressed();
    }

    private void signupVisible() {
        label.setText(R.string.label_signup);

        name.setVisibility(View.VISIBLE);
        birthday.setVisibility(View.VISIBLE);
        pass.setHint(R.string.hint_password_new);
        pass_r.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);
        isSignIn = false;
    }

    private void signinVisible() {
        label.setText(R.string.label_signin);

        name.setVisibility(View.GONE);
        birthday.setVisibility(View.GONE);
        pass.setHint(R.string.hint_password);
        pass_r.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        isSignIn = true;
    }
}
