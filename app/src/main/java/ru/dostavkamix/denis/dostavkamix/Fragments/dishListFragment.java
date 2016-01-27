package ru.dostavkamix.denis.dostavkamix.Fragments;


import android.app.ListFragment;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 09.01.16.
 */
public class dishListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setDivider(new ColorDrawable(this.getResources().getColor(R.color.separator_color)));
        getListView().setDividerHeight(1);

    }
}
