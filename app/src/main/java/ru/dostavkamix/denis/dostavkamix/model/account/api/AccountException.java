package ru.dostavkamix.denis.dostavkamix.model.account.api;

import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.BaseResponse;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

public class AccountException extends Exception {

    BaseResponse response;

    public AccountException(BaseResponse response) {
        this.response = response;
    }

    public BaseResponse.Errors getErrors() {
        return response.getErrors();
    }

    public String getMsg() {
        return response.getMsg();
    }
}
