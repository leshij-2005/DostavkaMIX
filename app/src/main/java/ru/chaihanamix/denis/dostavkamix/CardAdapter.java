package ru.chaihanamix.denis.dostavkamix;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;

import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 08.02.2016.
 */
public class CardAdapter extends PagerAdapter{

    LayoutInflater mInflater = (LayoutInflater) AppController.getInstance().getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    @Override
    public int getCount() {
        return BoxAdapter.object.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mInflater.inflate(R.layout.page_card, container, false);
        Dish dish = BoxAdapter.object.get(position);

        NetworkImageView dish_img_frag = (NetworkImageView) v.findViewById(R.id.dish_img_frag);
        TextViewPlus dish_name_frag = (TextViewPlus) v.findViewById(R.id.dish_name_frag);
        TextViewPlus dish_descript_frag = (TextViewPlus) v.findViewById(R.id.dish_descript_frag);
        TextViewPlus dish_price_frag = (TextViewPlus) v.findViewById(R.id.dish_price_frag);
        TextViewPlus dish_weight_frag = (TextViewPlus) v.findViewById(R.id.dish_weight_frag);

        dish_img_frag.setImageUrl(dish.getImjDish(), AppController.getInstance().getImageLoader());
        dish_name_frag.setText(dish.getNameDish());
        dish_descript_frag.setText(dish.getContent());
        dish_price_frag.setText(BoxAdapter.addRuble(String.valueOf(dish.getPriceDish())));

        dish_weight_frag.setText(dish.getWeight() + " " + dish.getMeasure());

        //dish_weight_frag.setText(BoxAdapter.object.get(position).getWeight() + "гр.");
        /*if(BoxAdapter.object.get(position).getNameCatalog().equals("Напитки")) {
            dish_weight_frag.setText(BoxAdapter.object.get(position).getWeight() + " мл.");
        } else dish_weight_frag.setText(BoxAdapter.object.get(position).getWeight() + " гр.");*/

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}