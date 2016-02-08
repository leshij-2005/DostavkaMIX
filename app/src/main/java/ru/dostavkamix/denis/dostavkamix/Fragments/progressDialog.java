package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 08.02.2016.
 */
public class progressDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
        dialog.setContentView(R.layout.dialog_add_bag);
        return dialog;
    }
}
