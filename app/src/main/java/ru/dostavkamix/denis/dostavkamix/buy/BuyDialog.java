package ru.dostavkamix.denis.dostavkamix.buy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
    @BindView(R.id.status_msg) TextView status_msg;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buy, null);
        builder.setView(view);
        unbinder = ButterKnife.bind(this, view);

        Dialog dialog = builder.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
        dialog.setCanceledOnTouchOutside(false);

        showBuying();
        return dialog;
    }

    @Override
    public void showAccount(Account account) {

    }

    @Override
    public void showSuccess() {
        fade(status_loading, false);
        fade(status_error, false);
        fade(status_success, true);
        status_msg.setText(R.string.msg_order_sending);
    }

    @Override
    public void showBuying() {
        fade(status_loading, true);
        fade(status_error, false);
        fade(status_success, false);
        status_msg.setText(R.string.msg_order_send);
    }

    @Override
    public void showError(Throwable throwable) {
        fade(status_loading, false);
        fade(status_error, true);
        fade(status_success, false);
        status_msg.setText(R.string.msg_order_error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
