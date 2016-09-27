
package ru.dostavkamix.denis.dostavkamix.model.content.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;

@Table
@Generated("org.jsonschema2pojo")
public class Item implements Parcelable{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("caption")
    private String caption;
    @SerializedName("discount")
    private boolean discount;
    @SerializedName("resourceId")
    private int resourceId;
    @SerializedName("total")
    private int total;

    public Item() {
    }

    public Item(String title, String id, Integer count) {
        this.title = title;
        this.id = id;
        this.count = count;
    }

    public Item(String title, int id, Integer count) {
        this.title = title;
        this.id = String.valueOf(id);
        this.count = count;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Item(String title, String id, String image, Integer price, String weight, String content) {
        this.title = title;
        this.id = id;
        this.image = image;
        this.price = price;
        this.weight = weight;
        this.content = content;
    }

    public Item(Parcel parcel) {
        this.title = parcel.readString();
        this.id = parcel.readString();
        this.image = parcel.readString();
        this.price = parcel.readInt();
        this.weight = parcel.readString();
        this.content = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.id);
        parcel.writeString(this.image);
        parcel.writeInt(this.price);
        parcel.writeString(this.weight);
        parcel.writeString(this.content);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
    };
}
