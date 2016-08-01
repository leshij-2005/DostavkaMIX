package ru.dostavkamix.denis.dostavkamix.Objects;

import android.graphics.Typeface;

/**
 * Created by den on 16.01.16.
 */
public class Dish extends Category {

    public static int TYPE = 1;

    private final String baseUrl = "http://chaihanamix.ru/";

    Typeface fontRub = null;
    Typeface fontReg = null;

    private int idDish = 0;
    private int priceDish = 0;
    private String weight = null;
    private String nameDish = null;
    private String content = null;
    private String imjDish = null;
    private int countOrder = 0;
    private int unic = 0;

    public Dish(int idCatalog, String nameCatalog, String imjCatalog, int idCategory, String nameCategory, String imjCategory, int idDish, int priceDish, String weight, String nameDish, String content, String imjDish, int unic) {
        super(idCatalog, nameCatalog, imjCatalog, idCategory, nameCategory, imjCategory);
        this.idDish = idDish;
        this.priceDish = priceDish;
        this.weight = weight;
        this.nameDish = nameDish;
        this.content = content;
        this.imjDish = baseUrl + imjDish;
        this.unic = unic;
    }

    public int getUnic() {
        return unic;
    }

    public int getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(int countOrder) {
        this.countOrder = countOrder;
    }

    public int getIdDish() {
        return idDish;
    }

    public int getPriceDish() {
        return priceDish;
    }

    public String getWeight() {
        return weight;
    }

    public String getNameDish() {
        return nameDish;
    }

    public String getContent() {
        return content;
    }

    public String getImjDish() {
        return imjDish;
    }
}
