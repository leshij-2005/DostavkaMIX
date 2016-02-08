package ru.dostavkamix.denis.dostavkamix;

/**
 * Created by den on 06.02.2016.
 */
public class Action {

    String title = "";
    String url = "";
    String content = "";

    public Action(String title, String url, String content) {
        this.title = title;
        this.url = url;
        this.content = content.trim();
    }
}
