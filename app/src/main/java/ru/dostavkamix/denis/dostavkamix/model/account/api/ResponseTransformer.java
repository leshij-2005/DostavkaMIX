package ru.dostavkamix.denis.dostavkamix.model.account.api;

import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.BaseResponse;
import rx.Observable;

/**
 * Created by Денис on 12.09.2016.
 *
 * @author Denis Tkachenko
 */

public class ResponseTransformer<T extends BaseResponse> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable.flatMap(response -> {
            if(!response.isStatus()) return Observable.error(new AccountException(response));

            return Observable.just(response);
        });
    }
}
