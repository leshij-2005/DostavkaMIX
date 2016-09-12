package ru.dostavkamix.denis.dostavkamix.model.account.api;

import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.BaseResponse;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

public class AccountException extends Exception {

    BaseResponse.Errors errors;

    public AccountException(BaseResponse.Errors errors) {
        this.errors = errors;
    }

    public BaseResponse.Errors getErrors() {
        return errors;
    }
}
