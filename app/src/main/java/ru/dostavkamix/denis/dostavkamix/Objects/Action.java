package ru.dostavkamix.denis.dostavkamix.Objects;

/**
 * Created by den on 06.02.2016.
 */
public class Action {

    public String title = "";
    public String url = "";
    public String content = "";

    public Action(String title, String url, String content) {
        this.title = title;
        this.url = url;
        this.content = content.trim();
    }
}
