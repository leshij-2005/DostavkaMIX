package ru.dostavkamix.denis.dostavkamix.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by Денис on 24.07.2016.
 *
 */

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private View icon;
    private View info;

    private View label;
    private View fields;

    private View signup;
    private View signin;

    private void inicializeUI() {
        icon = findViewById(R.id.icon);
        info = findViewById(R.id.info);

        label = findViewById(R.id.signup_label);
        fields = findViewById(R.id.signup_fields);
        signin = findViewById(R.id.signin);

        signup = findViewById(R.id.signup);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        BlurBehind.getInstance().setBackground(this);
        inicializeUI();

        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        icon.setVisibility(View.GONE);
        info.setVisibility(View.GONE);

        label.setVisibility(View.VISIBLE);
        fields.setVisibility(View.VISIBLE);
        signin.setVisibility(View.VISIBLE);
    }
}
