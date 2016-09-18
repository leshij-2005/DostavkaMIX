package ru.dostavkamix.denis.dostavkamix.model.content;

import retrofit2.http.GET;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Menu;
import rx.Observable;

/**
 * Created by Денис on 16.09.2016.
 *
 * @author Denis Tkachenko
 */

public interface ContentService {

    @GET("server/export.json")
    Observable<Menu> exportMenu();
}
