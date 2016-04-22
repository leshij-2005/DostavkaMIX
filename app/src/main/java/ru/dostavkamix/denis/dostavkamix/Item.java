package ru.dostavkamix.denis.dostavkamix;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by den on 19.04.16.
 */
public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, int position, View convertView);
    public long getId();
    public boolean isEnable();
}

