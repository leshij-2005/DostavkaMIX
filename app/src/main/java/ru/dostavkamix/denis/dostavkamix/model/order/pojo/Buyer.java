
package ru.dostavkamix.denis.dostavkamix.model.order.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Item;

@Generated("org.jsonschema2pojo")
public class Buyer {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("apartment")
    @Expose
    private String apartment;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The street
     */
    public String getStreet() {
        return street;
    }

    /**
     * 
     * @param street
     *     The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 
     * @return
     *     The house
     */
    public String getHouse() {
        return house;
    }

    /**
     * 
     * @param house
     *     The house
     */
    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * 
     * @return
     *     The apartment
     */
    public String getApartment() {
        return apartment;
    }

    /**
     * 
     * @param apartment
     *     The apartment
     */
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
