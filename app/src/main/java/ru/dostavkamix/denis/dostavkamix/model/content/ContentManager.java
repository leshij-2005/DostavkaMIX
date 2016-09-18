package ru.dostavkamix.denis.dostavkamix.model.content;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Catalog;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Category;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Item;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Menu;
import rx.Observable;

/**
 * Created by Денис on 16.09.2016.
 *
 * @author Denis Tkachenko
 */

public class ContentManager {

    ContentService contentService;

    public ContentManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chaihanamix.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        contentService = retrofit.create(ContentService.class);
    }

    public Observable<List<Item>> getItems() {
        return contentService.exportMenu()
                .map(this::getAllItemOfMenu);
    }

    public List<Item> getAllItemOfMenu(Menu menu) {
        List<Item> items = new ArrayList<>();

        for (Catalog catalog :
                menu.getCatalogs()) {
            for (Category category :
                    catalog.getCategories()) {
                items.addAll(category.getItems());
            }
        }

        return items;
    }
}
