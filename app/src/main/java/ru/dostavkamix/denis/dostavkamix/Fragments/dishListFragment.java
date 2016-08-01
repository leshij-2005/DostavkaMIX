package ru.dostavkamix.denis.dostavkamix.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import ru.dostavkamix.denis.dostavkamix.Adapters.BoxAdapter;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 09.01.16.
 */
public class dishListFragment extends Fragment {

    EditText searsh;
    ListView list_view;
    BoxAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);

        searsh = (EditText) v.findViewById(R.id.searsh);
        list_view = (ListView) v.findViewById(R.id.list_view);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(list_view != null && mAdapter != null)
        {

            searsh.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = searsh.getText().toString().toLowerCase();
                    mAdapter.filter(text);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            list_view.setAdapter(mAdapter);
        }
    }

    public void setAdapter(BoxAdapter adapter)
    {
        if(list_view != null)
        {
            searsh.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = searsh.getText().toString().toLowerCase();
                    mAdapter.filter(text);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            list_view.setAdapter(adapter);
            mAdapter = adapter;


        }
    }
}
