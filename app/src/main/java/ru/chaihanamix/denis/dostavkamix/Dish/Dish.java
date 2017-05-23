package ru.chaihanamix.denis.dostavkamix.Dish;

import android.graphics.Typeface;

/**
 * Created by den on 16.01.16.
 */
public class Dish extends Category {

    public static int TYPE = 1;

    Typeface fontRub = null;
    Typeface fontReg = null;

    private int idDish = 0;
    private int priceDish = 0;
    private String weight = null;
    private String measure = null;
    private String nameDish = null;
    private String content = null;
    private String imjDish = null;
    private int countOrder = 0;
    private boolean isNew = false;
    private boolean isPromo = false;
    private int unic = 0;

    public Dish(int idCatalog, String nameCatalog, String imjCatalog, int idCategory, String nameCategory, String imjCategory, int idDish, int priceDish, String weight, String measure, String nameDish, String content, String imjDish, boolean isNew, boolean isPromo, int unic) {
        super(idCatalog, nameCatalog, imjCatalog, idCategory, nameCategory, imjCategory);
        this.idDish = idDish;
        this.priceDish = priceDish;
        this.weight = weight;
        this.measure = measure;
        this.nameDish = nameDish;
        this.content = content;
        this.imjDish = imjDish;
        this.isNew = isNew;
        this.isPromo = isPromo;
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

    public String getMeasure() { return measure; }

    public String getNameDish() {
        return nameDish;
    }

    public String getContent() {
        return content;
    }

    public String getImjDish() {
        return imjDish;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isPromo() {
        return isPromo;
    }
}
