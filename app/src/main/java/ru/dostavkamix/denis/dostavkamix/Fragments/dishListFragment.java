package ru.dostavkamix.denis.dostavkamix.Fragments;


import android.app.ListFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import ru.dostavkamix.denis.dostavkamix.BoxAdapter;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 09.01.16.
 */
public class dishListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setDivider(new ColorDrawable(this.getResources().getColor(R.color.separator_color_2)));
        getListView().setDividerHeight(1);
        getListView().setVerticalScrollBarEnabled(false);

        

        Log.d("json", "Вот он.");

    }
}
