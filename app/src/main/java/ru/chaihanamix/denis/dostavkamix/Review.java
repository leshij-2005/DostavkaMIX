package ru.chaihanamix.denis.dostavkamix;

/**
 * Created by den on 06.02.2016.
 */
public class Review {

    String title = "";
    String subtitle = "";
    String content = "";

    public Review(String title, String subtitle, String content) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content.trim();
    }
}
