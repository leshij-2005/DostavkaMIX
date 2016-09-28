package ru.dostavkamix.denis.dostavkamix.model.preference;

/**
 * Created by denis on 28.09.16.
 *
 * @author Denis Tkachenko
 */

public interface PreferenceManager {

    boolean isLogin();

    String getToken();

    String getUser_Id();

    void setToken(String token);

    void setUser_Id(String user_id);
}
