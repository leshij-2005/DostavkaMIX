package ru.dostavkamix.denis.dostavkamix.Fragments.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Objects.User;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.UserCallback;
import ru.dostavkamix.denis.dostavkamix.UserHelper;

/**
 * Created by Денис on 02.08.2016.
 *
 */

public class ProfileEditFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileEditFragment";

    private EditText name;
    private EditText email;
    private EditText phone;

    private void findUI(View v) {
        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        phone = (EditText) v.findViewById(R.id.phone);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        findUI(v);


        UserHelper.getUser(AppController.getInstance().getUserToken(), getContext(), new UserCallback() {
            @Override
            public void onSuccess(User user) {
                name.setText(user.getName());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });

        return v;
    }
}
