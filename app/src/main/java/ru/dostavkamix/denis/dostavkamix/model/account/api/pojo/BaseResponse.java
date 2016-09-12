package ru.dostavkamix.denis.dostavkamix.model.account.api.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Денис on 04.09.2016.
 */

public class BaseResponse {

    @SerializedName("status")
    boolean status = true;
    @SerializedName("msg")
    String msg;
    @SerializedName("errors")
    private Errors errors;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {

        return status;
    }

    public String getMsg() {
        return msg;
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


    public class Errors {

        @SerializedName("name")
        private List<String> name = new ArrayList<String>();
        @SerializedName("phone")
        private List<String> phone = new ArrayList<String>();
        @SerializedName("birthday")
        private List<String> birthday = new ArrayList<String>();
        @SerializedName("email")
        private List<String> email = new ArrayList<String>();
        @SerializedName("password")
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
}
