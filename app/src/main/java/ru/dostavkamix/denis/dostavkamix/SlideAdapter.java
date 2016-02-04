package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Dish.Category;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 04.02.2016.
 */
public class SlideAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_SELECT = 2;

    private ArrayList<Category> mData = new ArrayList<Category>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private TreeSet<Integer> sectionSelect = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    SlideAdapter(Context context)
    {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < AppController.getInstance().getMainActivity().catalogs.size(); i++) {
            for (int k = 0; k < AppController.getInstance().getMainActivity().categories.size(); k++) {
                if(AppController.getInstance().getMainActivity().catalogs.get(i).getIdCatalog() == AppController.getInstance().getMainActivity().categories.get(k).getIdCatalog()){
                    addItem(AppController.getInstance().getMainActivity().categories.get(k));
                    break;
                }
            }

            for (int c = 0; c < AppController.getInstance().getMainActivity().categories.size(); c++) {
                if(AppController.getInstance().getMainActivity().catalogs.get(i).getIdCatalog() == AppController.getInstance().getMainActivity().categories.get(c).getIdCatalog())
                {
                    addSectionHeaderItem(AppController.getInstance().getMainActivity().categories.get(c));
                }
            }
        }



    }

    public void toggleTypeItem(final int position)
    {
        if(getItemViewType(position) == TYPE_SELECT)
        {
            sectionSelect.remove(position);
            sectionHeader.add(position);
        } else if(getItemViewType(position) == TYPE_SEPARATOR)
        {
            sectionSelect.add(position);
            sectionHeader.remove(position);
        }
    }


    public void addItem(final Category item)
    {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final Category item)
    {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    public void addSectionSelectItem(final Category item)
    {
        mData.add(item);
        sectionSelect.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(sectionHeader.contains(position)) return TYPE_SEPARATOR;
        else if(sectionSelect.contains(position)) return TYPE_SELECT;
        else return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if(convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case  TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.row_menu_slide, null);
                    holder.textView = (TextViewPlus) convertView.findViewById(R.id.row_menu_text);
                    ((TextViewPlus) convertView.findViewById(R.id.row_menu_text)).setText(mData.get(position).getNameCatalog());
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.item_menu_slide, null);
                    holder.textView = (TextViewPlus) convertView.findViewById(R.id.item_menu_text);
                    break;
                case TYPE_SELECT:
                    convertView = mInflater.inflate(R.layout.select_menu_slide, null);
                    holder.textView = (TextViewPlus) convertView.findViewById(R.id.select_menu_text);
            }
            holder.textView.setText(mData.get(position).getNameCategory());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextViewPlus textView;
    }
}
