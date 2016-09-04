package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Денис on 04.09.2016.
 */

public class UserResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("errors")
    @Expose
    private Errors errors;


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     *
     * @return
     * The errors
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Generated("org.jsonschema2pojo")
    public class Errors {

        @SerializedName("name")
        @Expose
        private List<String> name = new ArrayList<String>();
        @SerializedName("phone")
        @Expose
        private List<String> phone = new ArrayList<String>();
        @SerializedName("birthday")
        @Expose
        private List<String> birthday = new ArrayList<String>();
        @SerializedName("email")
        @Expose
        private List<String> email = new ArrayList<String>();
        @SerializedName("password")
        @Expose
        private List<String> password = new ArrayList<String>();

        /**
         *
         * @return
         * The name
         */
        public List<String> getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(List<String> name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The phone
         */
        public List<String> getPhone() {
            return phone;
        }

        /**
         *
         * @param phone
         * The phone
         */
        public void setPhone(List<String> phone) {
            this.phone = phone;
        }

        /**
         *
         * @return
         * The birthday
         */
        public List<String> getBirthday() {
            return birthday;
        }

        /**
         *
         * @param birthday
         * The birthday
         */
        public void setBirthday(List<String> birthday) {
            this.birthday = birthday;
        }

        /**
         *
         * @return
         * The email
         */
        public List<String> getEmail() {
            return email;
        }

        /**
         *
         * @param email
         * The email
         */
        public void setEmail(List<String> email) {
            this.email = email;
        }

        /**
         *
         * @return
         * The password
         */
        public List<String> getPassword() {
            return password;
        }

        /**
         *
         * @param password
         * The password
         */
        public void setPassword(List<String> password) {
            this.password = password;
        }

    }

    @Generated("org.jsonschema2pojo")
    public class Response {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("birthday")
        @Expose
        private String birthday;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The birthday
         */
        public String getBirthday() {
            return birthday;
        }

        /**
         * @param birthday The birthday
         */
        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The updatedAt
         */
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         * @param updatedAt The updated_at
         */
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        /**
         * @return The createdAt
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * @param createdAt The created_at
         */
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }
    }
}
