package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseViewStateFragment;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;

import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class EditFragment extends BaseViewStateFragment<EditView, EditPresenter> implements EditView {

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
        presenter.addAddress(new Account.Address());
    }

    @OnClick(R.id.save) void save_click() {
        presenter.updateUser(new Account(
                name.getText().toString(),
                phone.getText().toString(),
                email.getText().toString(),
                null));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AddressesAdapter(presenter.getUser().getAddresses());
        addresses.setAdapter(adapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile_edit;
    }

    @Override
    public ViewState createViewState() {
        return new EditViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public EditPresenter createPresenter() {
        return new EditPresenter();
    }
}
