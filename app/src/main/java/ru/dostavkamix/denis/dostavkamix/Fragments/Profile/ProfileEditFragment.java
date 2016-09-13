package ru.dostavkamix.denis.dostavkamix.Fragments.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ru.dostavkamix.denis.dostavkamix.content.profile.edit.AddressesAdapter;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Objects.Address;
import ru.dostavkamix.denis.dostavkamix.Objects.User;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.UserCallback;
import ru.dostavkamix.denis.dostavkamix.UserHelper;

/**
 * Created by Денис on 02.08.2016.
 *
 */

public class ProfileEditFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileEditFragment";

    private User tempUser;

    private View name_frame;
    private View email_frame;
    private View phone_frame;

    private EditText name;
    private EditText email;
    private EditText phone;

    private View old_pass_frame;
    private View new_pass_frame;
    private View new_pass_r_frame;

    private EditText old_pass;
    private EditText new_pass;
    private EditText new_pass_r;

    private View add;
    private RecyclerView addresses;
    private AddressesAdapter adapter;

    private View save;

    private void findUI(View v) {
        name_frame = v.findViewById(R.id.name_frame);
        email_frame = v.findViewById(R.id.email_frame);
        phone_frame = v.findViewById(R.id.phone_frame);

        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        phone = (EditText) v.findViewById(R.id.phone);

        old_pass_frame = v.findViewById(R.id.old_pass_frame);
        new_pass_frame = v.findViewById(R.id.new_pass_frame);
        new_pass_r_frame = v.findViewById(R.id.new_pass_r_frame);

        old_pass = (EditText) v.findViewById(R.id.old_pass);
        new_pass = (EditText) v.findViewById(R.id.new_pass);
        new_pass_r = (EditText) v.findViewById(R.id.new_pass_r);

        add = v.findViewById(R.id.add);
        addresses = (RecyclerView) v.findViewById(R.id.addresses);

        save = v.findViewById(R.id.save);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        findUI(v);

        name_frame.setOnClickListener(this);
        email_frame.setOnClickListener(this);
        phone_frame.setOnClickListener(this);

        old_pass_frame.setOnClickListener(this);
        new_pass_frame.setOnClickListener(this);
        new_pass_r_frame.setOnClickListener(this);

        add.setOnClickListener(this);
        save.setOnClickListener(this);

        if(AppController.getInstance().getUser() != null) {
            tempUser = AppController.getInstance().getUser();
            viewUser();
        } else {
            UserHelper.getUser(AppController.getInstance().getUserToken(), getContext(), new UserCallback() {
                @Override
                public void onSuccess(User user) {
                    tempUser = AppController.getInstance().getUser();
                    viewUser();
                }

                @Override
                public void onError(String error) {
                    Log.d(TAG, "onError: " + error);
                }
            });
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name_frame:
                name.requestFocus();
                name.setSelection(name.getText().length());
                break;
            case R.id.email_frame:
                email.requestFocus();
                email.setSelection(email.getText().length());
                break;
            case R.id.phone_frame:
                phone.requestFocus();
                phone.setSelection(phone.getText().length());
                break;
            case R.id.old_pass_frame:
                old_pass.requestFocus();
                old_pass.setSelection(old_pass.getText().length());
                break;
            case R.id.new_pass_frame:
                new_pass.requestFocus();
                new_pass.setSelection(new_pass.getText().length());
                break;
            case R.id.new_pass_r_frame:
                new_pass_r.requestFocus();
                new_pass_r.setSelection(new_pass_r.getText().length());
                break;
            case R.id.save:
                updateUser();
                break;
            case R.id.add:
                Log.d(TAG, "onClick: add");
                if(tempUser.getAddresses().size() >= 3) break;
                tempUser.addAddress(new Address());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void viewUser() {
        name.setText(tempUser.getName());
        email.setText(tempUser.getEmail());
        phone.setText(tempUser.getPhone());


        if(tempUser.getAddresses().size() == 0) tempUser.getAddresses().add(new Address());
        //adapter = new AddressesAdapter(tempUser.getAddresses());
        addresses.setAdapter(adapter);
        addresses.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateUser() {
        tempUser.setName(name.getText().toString());
        tempUser.setEmail(email.getText().toString());
        tempUser.setPhone(phone.getText().toString());

        List<Address> tempAddresses = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            //tempAddresses.add(((AddressesAdapter.ViewHolder) addresses.findViewHolderForAdapterPosition(i)).getAddress());
        }
        //adapter.updateAddresses(tempAddresses);
        tempUser.setAddresses(tempAddresses);

        if(old_pass.getText().length() > 0 && new_pass.getText().length() > 0) {
            UserHelper.updateUser(tempUser, new_pass.getText().toString(), old_pass.getText().toString(), getActivity(), null);
        } else UserHelper.updateUser(tempUser, null, null, getActivity(), null);
    }
}
