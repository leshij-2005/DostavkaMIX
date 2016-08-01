package ru.dostavkamix.denis.dostavkamix.Objects;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Objects.Dish;

/**
 * Created by den on 07.02.2016.
 */
public class Buyer {

    public String name;
    public String phone;
    public String street;
    public String house;
    public String apartament;
    public String type;
    public String money;

    public ArrayList<item> items = new ArrayList<>();

    public Buyer(String name, String phone, String street, String house, String apartament, String type, String money, ArrayList<Dish> items) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.house = house;
        this.apartament = apartament;
        this.type = type;
        this.money = money;

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
