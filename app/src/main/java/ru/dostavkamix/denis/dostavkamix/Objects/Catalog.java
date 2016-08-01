package ru.dostavkamix.denis.dostavkamix.Objects;

/**
 * Created by den on 16.01.16.
 */
public class Catalog {

    public static int TYPE = 0;

    private int idCatalog = 0;
    private String nameCatalog = null;
    private String imjCatalog = null;

    public Catalog(int idCatalog, String nameCatalog, String imjCatalog) {
        this.idCatalog = idCatalog;
        this.nameCatalog = nameCatalog;
        this.imjCatalog = imjCatalog;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public String getNameCatalog() {
        return nameCatalog;
    }

    public String getImjCatalog() {
        return imjCatalog;
    }
}
