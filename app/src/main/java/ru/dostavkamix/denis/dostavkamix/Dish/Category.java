package ru.dostavkamix.denis.dostavkamix.Dish;

/**
 * Created by den on 16.01.16.
 */
public class Category extends Catalog {

    private int idCategory = 0;
    private String nameCategory = null;
    private String imjCategory = null;

    public Category(int idCatalog, String nameCatalog, String imjCatalog, int idCategory, String nameCategory, String imjCategory) {
        super(idCatalog, nameCatalog, imjCatalog);
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.imjCategory = imjCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public String getImjCategory() {
        return imjCategory;
    }
}
