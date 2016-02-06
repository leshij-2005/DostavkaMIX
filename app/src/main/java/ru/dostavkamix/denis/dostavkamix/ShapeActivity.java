package ru.dostavkamix.denis.dostavkamix;

import android.app.Activity;
import android.os.Bundle;

import ru.dostavkamix.denis.dostavkamix.blurbehind.BlurBehind;

/**
 * Created by den on 07.02.2016.
 */
public class ShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shape);

        BlurBehind.getInstance().setBackground(this);
    }
}
