package ru.dostavkamix.denis.dostavkamix.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by Денис on 24.07.2016.
 *
 */

public class IntroActivity extends AppCompatActivity {


    private View signup;

    private void inicializeUI() {
        signup = findViewById(R.id.signup);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        BlurBehind.getInstance().setBackground(this);
        inicializeUI();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, SignUpActivity.class));
            }
        });
    }

}
