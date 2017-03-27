package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;


public class Question {
    @SerializedName("question_id")
    private Integer questionID;
    @SerializedName("category_id")
    private Integer categoryID;
    @SerializedName("question_text")
    private String questionText;
    @SerializedName("question_type_id")
    private Integer questionTypeID;


    public Question(Integer categoryID, Integer questionID, String questionText, Integer questionTypeID) {

        this.categoryID = categoryID;
        this.questionID = questionID;
        this.questionText = questionText;
        this.questionTypeID = questionTypeID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(Integer questionTypeID) {
        this.questionTypeID = questionTypeID;
    }
}
