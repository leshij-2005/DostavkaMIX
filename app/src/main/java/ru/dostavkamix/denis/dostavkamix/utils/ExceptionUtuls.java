package ru.dostavkamix.denis.dostavkamix.utils;

import ru.dostavkamix.denis.dostavkamix.login.PassNotMatch;
import ru.dostavkamix.denis.dostavkamix.model.account.api.AccountException;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class ExceptionUtuls {

    public static String getMsg(Throwable throwable) {
        if(throwable instanceof AccountException) return ((AccountException) throwable).getMsg();

        return "Неизвестная ошибка";
    }

    public static ErrorType getErrorType(Throwable throwable) {
        if(throwable instanceof AccountException) {
            AccountException accountException = (AccountException) throwable;
            if(accountException.getErrors() == null) return ErrorType.NONE;

            if(!accountException.getErrors().getName().isEmpty()) return ErrorType.NAME;
            if(!accountException.getErrors().getPhone().isEmpty()) return ErrorType.PHONE;
            if(!accountException.getErrors().getEmail().isEmpty()) return ErrorType.EMAIL;
            if(!accountException.getErrors().getBirthday().isEmpty()) return ErrorType.BIRTHDAY;
            if(!accountException.getErrors().getPassword().isEmpty()) return ErrorType.PASSWORD;
        }

        if(throwable instanceof PassNotMatch) return ErrorType.PASSWORD_R;

        return ErrorType.NONE;
    }

    public enum ErrorType { NAME, EMAIL, BIRTHDAY, PASSWORD, PASSWORD_R, PHONE, NONE};
}
