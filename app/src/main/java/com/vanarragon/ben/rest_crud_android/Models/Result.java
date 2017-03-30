package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;


public class Result {
    @SerializedName("result_id")
    private String resultID;
    @SerializedName("result_score")
    private Integer resultScore;
    @SerializedName("user_ID")
    private String userID;
    @SerializedName("category_id")
    private String categoryID;
    @SerializedName("total_length")
    private int totalLength;
    @SerializedName("date_written")
    private String dateWritten;


    public Result(String categoryID, String dateWritten, String resultID, Integer resultScore, int totalLength, String userID) {
        this.categoryID = categoryID;
        this.dateWritten = dateWritten;
        this.resultID = resultID;
        this.resultScore = resultScore;
        this.totalLength = totalLength;
        this.userID = userID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(String dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public Integer getResultScore() {
        return resultScore;
    }

    public void setResultScore(Integer resultScore) {
        this.resultScore = resultScore;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
