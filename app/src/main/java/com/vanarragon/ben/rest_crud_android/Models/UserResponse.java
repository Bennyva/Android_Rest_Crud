package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("Error")
    private boolean error;
    @SerializedName("Message")
    private String message;
    @SerializedName("Users")
    private List<User> users;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

