package ru.chaihanamix.denis.dostavkamix.Fragments;

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

import java.util.Calendar;

import me.drakeet.materialdialog.MaterialDialog;
import ru.chaihanamix.denis.dostavkamix.AppController;
import ru.chaihanamix.denis.dostavkamix.BagSwipeAdapter;
import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.MainActivity;
import ru.chaihanamix.denis.dostavkamix.OrderActivity;
import ru.chaihanamix.denis.dostavkamix.R;


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
    MaterialDialog inposOrderDialog, closeTerminalDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bag, container, false);

        AppController.getInstance().selectMenu(7, true);
        listView = (ListView) v.findViewById(R.id.list_bag);
        sale = (TextViewPlus) v.findViewById(R.id.sale);
        withoutSale = (TextViewPlus) v.findViewById(R.id.without_sale);
        withSale = (TextViewPlus) v.findViewById(R.id.with_sale);
        text_but_order = (TextViewPlus) v.findViewById(R.id.text_but_order);
        but_order = (RelativeLayout) v.findViewById(R.id.but_to_order);

        //listView.setAdapter(new BagAdapter(AppController.getInstance().getApplicationContext()));
        listView.setAdapter(new BagSwipeAdapter(AppController.getInstance().getApplicationContext()));

        inposOrderDialog = new MaterialDialog(AppController.getInstance().getMainActivity())
                .setMessage("Обращаем Ваше внимание, что к сумме заказа будет прибавленно 150 руб. за доставку. Подробности уточняйте у оператора или в разделе \"Условия доставки\"")
                .setPositiveButton("Закрыть", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inposOrderDialog.dismiss();
                        startOrderActivity();
                    }
                });

        closeTerminalDialog = new MaterialDialog(AppController.getInstance().getMainActivity())
                .setMessage("С 00:00 до 11:00 заказы не принимаются. Желаете оформить заказ на другое время?")
                .setPositiveButton("Да", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeTerminalDialog.dismiss();

                        if(AppController.getInstance().getSale() == 0) {
                            inposOrderDialog.show();
                        } else {
                            startOrderActivity();
                        }
                    }
                })
                .setNegativeButton("Нет", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeTerminalDialog.dismiss();
                    }
                });

        but_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                int hourResult = now.get(Calendar.HOUR);

                if (hourResult > 23 || hourResult < 11 ) {
                    closeTerminalDialog.show();
                } else if(AppController.getInstance().getSale() == 0) {
                    inposOrderDialog.show();
                } else {
                    startOrderActivity();
                }
            }
        });

        updateFragPrice();

        return v;
    }

    private void startOrderActivity() {
        MainActivity mainActivity = AppController.getInstance().getMainActivity();

        Intent intent = new Intent(mainActivity.getApplicationContext(), OrderActivity.class);
        startActivity(intent);
        mainActivity.overridePendingTransition(R.anim.slide_in_bottom, android.R.anim.fade_out);
    }

    public void updateFragPrice()
    {
        int totalWithSale = AppController.getInstance().getWithSale();
        int totalWithoutSale = AppController.getInstance().getBagPrice();

        double s = totalWithoutSale > 0 ? ((totalWithoutSale - totalWithSale) * 100 / totalWithoutSale) : 0;

        sale.setText(String.valueOf((int) s) + "%");

        withSale.setText(addR(String.valueOf(totalWithSale)));
        withoutSale.setText(addR(String.valueOf(totalWithoutSale)));

        text_but_order.setText("Оформить за " + addR(String.valueOf(totalWithSale)));
    }

    private String addR(String s)
    {
        String result = "";
        if(s.length() < 4) result = s + " руб.";
        else result = s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1 + (s.length() - 4)) + " руб.";

        return result;
    }
}
