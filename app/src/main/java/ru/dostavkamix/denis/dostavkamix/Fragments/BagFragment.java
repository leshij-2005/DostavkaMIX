package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import me.drakeet.materialdialog.MaterialDialog;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.BagSwipeAdapter;
import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.OrderActivity;
import ru.dostavkamix.denis.dostavkamix.R;


/**
 * Created by den on 29.01.2016.
 */
public class BagFragment extends Fragment {


    ListView listView;
    TextViewPlus sale;
    TextViewPlus withoutSale;
    TextViewPlus withSale;
    TextViewPlus text_but_order;
    RelativeLayout but_order;
    MaterialDialog inposOrderDialog;
    MaterialDialog iphoneDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bag, container, false);

        listView = (ListView) v.findViewById(R.id.list_bag);
        sale = (TextViewPlus) v.findViewById(R.id.sale);
        withoutSale = (TextViewPlus) v.findViewById(R.id.without_sale);
        withSale = (TextViewPlus) v.findViewById(R.id.with_sale);
        text_but_order = (TextViewPlus) v.findViewById(R.id.text_but_order);
        but_order = (RelativeLayout) v.findViewById(R.id.but_to_order);

        //listView.setAdapter(new BagAdapter(AppController.getInstance().getApplicationContext()));
        listView.setAdapter(new BagSwipeAdapter(AppController.getInstance().getApplicationContext()));

        inposOrderDialog = new MaterialDialog(AppController.getInstance().getMainActivity())
                .setMessage("Извините, минимальный заказ от 500 рублей!")
                .setPositiveButton("Закрыть", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inposOrderDialog.dismiss();
                    }
                });
        iphoneDialog = new MaterialDialog(AppController.getInstance().getMainActivity())
                .setMessage("Сделай заказ от 1000 рублей и учавствуй в розыгрыше iPhone 6s. Чем больше заказов, тем ближе айфон!")
                .setPositiveButton("Закрыть", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iphoneDialog.dismiss();
                        Log.d("json", "new activity");
                        Intent intent = new Intent(AppController.getInstance().getMainActivity().getApplicationContext(), OrderActivity.class);
                        startActivity(intent);
                        AppController.getInstance().getMainActivity().overridePendingTransition(R.anim.slide_in_bottom, android.R.anim.fade_out);
                    }
                });

        but_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppController.getInstance().getWithSale() < 500)
                    inposOrderDialog.show();
                else {
                    if(AppController.getInstance().getWithSale() < 1000)
                    {
                        iphoneDialog.show();
                        return;
                    }
                    Log.d("json", "new activity");
                    Intent intent = new Intent(AppController.getInstance().getMainActivity().getApplicationContext(), OrderActivity.class);
                    startActivity(intent);
                    AppController.getInstance().getMainActivity().overridePendingTransition(R.anim.slide_in_bottom, android.R.anim.fade_out);
                    //AppController.getInstance().getMainActivity().overridePendingTransition();
                }
            }
        });

        updateFragPrie();

        return v;
    }

    public void updateFragPrie()
    {
        sale.setText(String.valueOf(AppController.getInstance().getSale()) + "%");

        withSale.setText(addR(String.valueOf(AppController.getInstance().getWithSale())));
        withoutSale.setText(addR(String.valueOf(AppController.getInstance().getWithoutSale())));

        text_but_order.setText("Оформить за " + addR(String.valueOf(AppController.getInstance().getWithSale())));
    }

    private String addR(String s)
    {
        String result = "";
        if(s.length() < 4) result = s + " руб.";
        else result = s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1 + (s.length() - 4)) + " руб.";

        return result;
    }
}
