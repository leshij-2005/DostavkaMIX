package ru.dostavkamix.denis.dostavkamix.buy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;

import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.fade;

/**
 * Created by den on 08.02.2016.
 */
public class BuyDialog extends DialogFragment implements BuyView{

    Unbinder unbinder;

    @BindView(R.id.status_loading) View status_loading;
    @BindView(R.id.status_success) View status_success;
    @BindView(R.id.status_error) View status_error;
    @BindView(R.id.status_msg) View status_msg;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
        dialog.setContentView(R.layout.dialog_buy);

        unbinder = ButterKnife.bind(dialog);
        Log.d("BuyDialog", "onCreateDialog: binding");
        return dialog;
    }

    @Override
    public void showAccount(Account account) {

    }

    @Override
    public void showSuccess() {
        Log.d("BuyDialog", "showSuccess: ");
        fade(status_loading, false);
        fade(status_error, false);
        fade(status_success, true);
    }

    @Override
    public void showBuying() {
        fade(status_loading, true);
        fade(status_error, false);
        fade(status_success, false);
    }

    @Override
    public void showError() {
        fade(status_loading, false);
        fade(status_error, true);
        fade(status_success, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
