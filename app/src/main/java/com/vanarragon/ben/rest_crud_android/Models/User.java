package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class User {
    @SerializedName("user_id")
    private String userID;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("last_log_in")
    private String lastLogIn;
    @SerializedName("log_in_count")
    private int logInCount;
    @SerializedName("user_first_name")
    private String userFirstName;
    @SerializedName("user_last_name")
    private String userLastName;

    public User(String lastLogIn, int logInCount, String userEmail, String userFirstName, String userID, String userLastName) {
        this.lastLogIn = lastLogIn;
        this.logInCount = logInCount;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userID = userID;
        this.userLastName = userLastName;
    }

    public String getLastLogIn() {
        return lastLogIn;
    }

    public void setLastLogIn(String lastLogIn) {
        this.lastLogIn = lastLogIn;
    }

    public int getLogInCount() {
        return logInCount;
    }

    public void setLogInCount(int logInCount) {
        this.logInCount = logInCount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
}
