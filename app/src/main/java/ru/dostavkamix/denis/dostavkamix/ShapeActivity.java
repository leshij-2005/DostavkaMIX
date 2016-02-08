package ru.dostavkamix.denis.dostavkamix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ru.dostavkamix.denis.dostavkamix.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.blurbehind.OnBlurCompleteListener;

/**
 * Created by den on 07.02.2016.
 */
public class ShapeActivity extends Activity {

    ShapeActivity mShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShape = this;

        setContentView(R.layout.activity_shape);

        BlurBehind.getInstance().setBackground(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BlurBehind.getInstance().execute(AppController.getInstance().getMainActivity(), new OnBlurCompleteListener() {
                            @Override
                            public void onBlurComplete() {
                                Intent intent = new Intent(AppController.getInstance().getMainActivity(), HelpActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            }
                        }, 1);
                    }
                });
            }
        }).start();

    }
}
