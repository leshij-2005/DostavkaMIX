package ru.dostavkamix.denis.dostavkamix.SlideMenu;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Dish.Category;
import ru.dostavkamix.denis.dostavkamix.MainActivity;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by d.tkachenko on 26.04.2016.
 */
public class SlideAdapter extends ArrayAdapter {

    public static final int TYPE_CATALOG = 0;
    public static final int TYPE_SUBCATALOG = 1;
    public static final int TYPE_SUBCATALOG_ACTIVE = 2;

    private ListViewItem[] objects;
    public HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();

    public void setChecked(int position, boolean b)
    {
        myChecked.put(position, b);
        notifyDataSetChanged();
    }

    public void toggleChecked(int position) {
        for (int i = 0; i < objects.length; i++) {
            myChecked.put(i, false);
        }
        myChecked.put(position, true);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return objects[position].getType();
    }

    public SlideAdapter(Context context, int resource, ListViewItem[] objects) {
        super(context, resource, objects);
        this.objects = objects;

        for (int i = 0; i < objects.length; i++) {
            myChecked.put(i, false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ListViewItem listViewItem = objects[position];
        int listViewItemType = getItemViewType(position);

        if (convertView == null) {
            if (listViewItemType == TYPE_CATALOG) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.slide_catalog, null);
            } else if (listViewItemType == TYPE_SUBCATALOG) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.slide_subcatalog, null);
            }

            TextViewPlus textViewPlus = (TextViewPlus) convertView.findViewById(R.id.slide_text);
            viewHolder = new ViewHolder(textViewPlus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Boolean checked = myChecked.get(position);
        if (objects[position].getType() != TYPE_CATALOG) {
            if (checked != null) {
                //checkBut.setChecked(checked);
                if (checked) {
                    //viewHolder.getText().setCustomFont(AppController.getInstance(), "fonts/GothaProBol.otf");
                    viewHolder.getText().setTextColor(Color.WHITE);
                    //Log.d("click", "set style text on");
                } else {
                    //viewHolder.getText().setCustomFont(AppController.getInstance(), "fonts/GothaProReg.otf");
                    viewHolder.getText().setTextColor(AppController.getInstance().getResources().getColor(R.color.menu_category_color));
                    //Log.d("click", "set style text off");

                }
                notifyDataSetChanged();
            }
        }

            viewHolder.getText().setText(listViewItem.getText());
            final ViewHolder finalViewHolder = viewHolder;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (objects[position].getType() != TYPE_CATALOG) {
                        AppController.getInstance().getMainActivity().slideMuneDrawer.closeDrawer();
                        Log.d("click", String.valueOf(finalViewHolder.getText().getText()));
                        toggleChecked(position);

                        //Category categ = AppController.getInstance().getMainActivity().getCategoryOfName(String.valueOf(objects[position].getText()));
                        Category categ = AppController.getInstance().getMainActivity().getCategotyOfId(objects[position].getId());
                        if (categ != null) {
                            Log.d("json", "Category ID: " + categ.getIdCategory());
                        } else Log.d("json", "Not found category =(");
                        AppController.getInstance().getMainActivity().updateListDish(AppController.getInstance().getMainActivity().getDishOfCategory(categ, AppController.getInstance().getMainActivity().dishs));
                        notifyDataSetChanged();
                    }
                }
            });

            return convertView;
        }


    public void setItems(ListViewItem[] objects) {
        this.objects = objects;
    }
}
