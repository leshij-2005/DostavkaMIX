package ru.dostavkamix.denis.dostavkamix.SlideMenu;

import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by d.tkachenko on 26.04.2016.
 */
public class ViewHolder {
    TextViewPlus text;

    public ViewHolder(TextViewPlus text) {
        this.text = text;
    }

    public TextViewPlus getText() {
        return text;
    }

    public void setText(TextViewPlus text) {
        this.text = text;
    }

}
