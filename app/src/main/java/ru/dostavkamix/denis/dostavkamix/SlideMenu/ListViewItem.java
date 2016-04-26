package ru.dostavkamix.denis.dostavkamix.SlideMenu;

/**
 * Created by d.tkachenko on 26.04.2016.
 */
public class ListViewItem {
    private String text;
    private int type;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public ListViewItem(String text, int type, int id) {
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
