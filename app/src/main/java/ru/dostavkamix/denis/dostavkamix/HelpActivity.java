package ru.dostavkamix.denis.dostavkamix;

import android.app.Activity;
import android.os.Bundle;

import ru.dostavkamix.denis.dostavkamix.blurbehind.BlurBehind;

/**
 * Created by den on 07.02.2016.
 */
public class HelpActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);

        BlurBehind.getInstance().setBackground(this);
    }
}
