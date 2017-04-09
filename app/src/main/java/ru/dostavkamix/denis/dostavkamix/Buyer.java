package ru.dostavkamix.denis.dostavkamix;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 07.02.2016.
 */
public class Buyer {

    public String name;
    public String phone;
    public String street;
    public String home;
    public String apartment;
    public String delivery;
    public String payment;
    public String cash;
    public String client;
    public String sum;
    public String date;

    public ArrayList<item> items = new ArrayList<>();

    public Buyer(String name, String phone, String street, String home, String apartment, String delivery, String payment, String cash, String sum, String date, ArrayList<Dish> items) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.home = home;
        this.apartment = apartment;
        this.delivery = delivery;
        this.payment = payment;
        this.cash = cash;
        this.client = "android";
        this.sum = sum;
        this.date = date;

        for (int i = 0; i < items.size(); i++) {
            this.items.add(new item(items.get(i).getNameDish(), String.valueOf(items.get(i).getCountOrder()), String.valueOf(items.get(i).getIdDish())));
        }
    }

    private class item {
        public String title;
        public String count;
        public String id;

        public item(String title, String count, String id) {
            this.title = title;
            this.count = count;
            this.id = id;
        }
    }
}
