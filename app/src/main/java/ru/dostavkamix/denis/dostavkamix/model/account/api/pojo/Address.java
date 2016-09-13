
package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Address {

    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("porch")
    @Expose
    private String porch;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("apartment")
    @Expose
    private String apartment;

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
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The porch
     */
    public String getPorch() {
        return porch;
    }

    /**
     * 
     * @param porch
     *     The porch
     */
    public void setPorch(String porch) {
        this.porch = porch;
    }

    /**
     * 
     * @return
     *     The floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * 
     * @param floor
     *     The floor
     */
    public void setFloor(String floor) {
        this.floor = floor;
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

}
