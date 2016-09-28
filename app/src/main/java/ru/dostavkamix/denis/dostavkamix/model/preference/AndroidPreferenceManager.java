package ru.dostavkamix.denis.dostavkamix.model.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by denis on 28.09.16.
 *
 * @author Denis Tkachenko
 */

public class AndroidPreferenceManager implements PreferenceManager {

    private SharedPreferences preferences;
    private Context context;

    @Inject
    public AndroidPreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE);
    }

    @Override
    public boolean isLogin() {
        return (preferences.getString(context.getString(R.string.token_key), null) != null
                && (preferences.getString(context.getString(R.string.user_id_key), null)) != null);
    }

    @Nullable
    @Override
    public String getToken() {
        return preferences.getString(context.getString(R.string.token_key), null);
    }

    @Nullable
    @Override
    public String getUser_Id() {
        return preferences.getString(context.getString(R.string.user_id_key), null);
    }

    @Override
    public void setToken(String token) {
        preferences.edit()
                .putString(context.getString(R.string.token_key), token)
                .commit();
    }

    @Override
    public void setUser_Id(String user_id) {
        preferences.edit()
                .putString(context.getString(R.string.user_id_key), user_id)
                .commit();
    }
}
