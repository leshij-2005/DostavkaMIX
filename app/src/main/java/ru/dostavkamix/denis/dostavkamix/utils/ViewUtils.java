package ru.dostavkamix.denis.dostavkamix.utils;

import android.widget.EditText;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class ViewUtils {

    public static void focus(EditText View) {
        View.requestFocus();
        View.setSelection(View.getText().length());
    }
}
