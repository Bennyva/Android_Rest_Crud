package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class User {
    @SerializedName("user_id")
    private String userID;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_password")
    private String userPassword;
    @SerializedName("user_join_date")
    private String userJoinDate;

    public User(String userID, String userEmail, String userPassword, String userJoinDate){
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userJoinDate = userJoinDate;


    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserJoinDate() {
        return userJoinDate;
    }

    public void setUserJoinDate(String userJoinDate) {
        this.userJoinDate = userJoinDate;
    }


}
