
package ru.dostavkamix.denis.dostavkamix.model.content.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Menu {

    @SerializedName("catalogs")
    @Expose
    private List<Catalog> catalogs = new ArrayList<Catalog>();

    /**
     * 
     * @return
     *     The catalogs
     */
    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    /**
     * 
     * @param catalogs
     *     The catalogs
     */
    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

}
