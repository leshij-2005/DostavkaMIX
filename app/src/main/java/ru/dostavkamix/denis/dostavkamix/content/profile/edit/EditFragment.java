package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hkm.ui.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseLceFragment;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.api.AccountException;

import static android.content.Context.MODE_PRIVATE;
import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;
import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.isEmpty;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class EditFragment extends BaseLceFragment<LinearLayout, Account, EditView, EditPresenter> implements EditView {

    private static final String TAG = "EditFragment";
    private Account currentAccount;
    private AddressesAdapter adapter;

    @OnClick(R.id.name_frame) void name_frame_click() { focus(name);}
    @OnClick(R.id.email_frame) void email_frame_click() { focus(email);}
    @OnClick(R.id.phone_frame) void phone_frame_click() { focus(phone);}

    @BindView(R.id.name) EditText name;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.phone) EditText phone;

    @OnClick(R.id.old_pass_frame) void old_pass_frame_click() { focus(old_pass);}
    @OnClick(R.id.new_pass_frame) void new_pass_frame_click() { focus(new_pass);}
    @OnClick(R.id.new_pass_r_frame) void new_pass_r__frame_click() { focus(new_pass_r);}

    @BindView(R.id.old_pass) EditText old_pass;
    @BindView(R.id.new_pass) EditText new_pass;
    @BindView(R.id.new_pass_r) EditText new_pass_r;

    @BindView(R.id.addresses) RecyclerView addresses;

    @BindView(R.id.save) ActionProcessButton save;

    @OnClick(R.id.add) void add_click() {
        currentAccount.getAddresses().add(new Account.Address("", "", ""));
        adapter.notifyDataSetChanged();
    }
    @OnClick(R.id.save) void save_click() {
        currentAccount.setName(name.getText().toString());
        currentAccount.setEmail(email.getText().toString());
        currentAccount.setPhone(phone.getText().toString());

        for (int i = 0; i < currentAccount.getAddresses().size(); i++) {
            currentAccount.getAddresses().set(i, ((AddressesAdapter.ViewHolder) addresses.findViewHolderForAdapterPosition(i)).getAddress());
        }
        
        presenter.updateAccount(currentAccount);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if(e instanceof AccountException) return ((AccountException) e).getMsg();
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile_edit;
    }

    @NonNull
    @Override
    public EditPresenter createPresenter() {
        return new EditPresenter();
    }

    @Override
    public void setData(Account data) {
        Log.d(TAG, "setData: addresses count: " + data.getAddresses().size());
        for (Account.Address address :
                data.getAddresses()) {
            Log.d(TAG, "setData: street: " + address.getStreet());
        }

        adapter = new AddressesAdapter(data.getAddresses());
        addresses.setAdapter(adapter);
        addresses.setLayoutManager(new LinearLayoutManager(getContext()));

        name.setText(data.getName());
        email.setText(data.getEmail());
        phone.setText(data.getPhone());

        currentAccount = data;
        if(save.getProgress() != 0) save.setProgress(100);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadAccount();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        Log.d(TAG, "getErrorMessage: ");
        e.printStackTrace();
        if(pullToRefresh) save.setProgress(0);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);

        if(pullToRefresh) {
            save.setProgress(30);
        }
    }
}
