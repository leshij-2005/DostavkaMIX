package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.hkm.ui.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseLceFragment;
import ru.dostavkamix.denis.dostavkamix.base.BaseViewStateFragment;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;

import static android.content.Context.MODE_PRIVATE;
import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;

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

    @OnClick(R.id.add) void add_click() {
       // presenter.addAddress(new Account.Address());
        currentAccount.getAddresses().add(new Account.Address("", "", ""));
        adapter.notifyDataSetChanged();
    }

    @BindView(R.id.save) ActionProcessButton save;
    @OnClick(R.id.save) void save_click() {
        presenter.updateAccount(currentAccount);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToken();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        e.printStackTrace();
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile_edit;
    }

    @NonNull
    @Override
    public LceViewState<Account, EditView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public Account getData() {
        return currentAccount;
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

        currentAccount = data;
        if(save.getProgress() != 0) save.setProgress(100);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadAccount();
    }

    private void getToken() {
        String token = getActivity().getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
                .getString(getString(R.string.token_key), null);

        if(token != null) presenter.setCurrentToken(token);
        Log.d("ContentActivity", "getToken: " + token);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        Log.d(TAG, "getErrorMessage: ");
        e.printStackTrace();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);

        if(pullToRefresh) {
            save.setProgress(30);
        }
    }
}
