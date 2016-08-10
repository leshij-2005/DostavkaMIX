package ru.dostavkamix.denis.dostavkamix;

import ru.dostavkamix.denis.dostavkamix.Objects.User;

/**
 * Created by Денис on 06.08.2016.
 */

public interface UserCallback {

    void onSuccess(User user);

    void onError(String error);
}
