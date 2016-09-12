package ru.dostavkamix.denis.dostavkamix.model.account.api;

import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.User;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.UserResponse;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

public class Utils {
    
    static Account User2Account(User user) {
        return new Account(user.getName(), user.getPhone(), user.getEmail(), user.getBirthday());
    }

    static Account UserResponse2Account(UserResponse user) {
        return new Account(user.getResponse().getName(), user.getResponse().getPhone(), user.getResponse().getEmail(), user.getResponse().getBirthday());
    }

    static User Account2User(Account account) {
        return new User(account.getName(), account.getEmail(), account.getPhone(), account.getBirthday(), account.getPassword());
    }
}
