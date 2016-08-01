package ru.dostavkamix.denis.dostavkamix.Objects;

/**
 * Created by den on 06.02.2016.
 */
public class Review {

    public String title = "";
    public String subtitle = "";
    public String content = "";

    public Review(String title, String subtitle, String content) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content.trim();
    }
}
