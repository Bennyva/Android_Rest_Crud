package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;


public class Answer {
    @SerializedName("answer_id")
    private Integer answerID;
    @SerializedName("question_id")
    private Integer questionID;
    @SerializedName("isCorrect")
    private Integer isCorrect;
    @SerializedName("answer_text")
    private String answerText;
    @SerializedName("category_id")
    private String categoryID;


    public Answer(Integer answerID, String answerText, String categoryID, Integer isCorrect, Integer questionID) {
        this.answerID = answerID;
        this.answerText = answerText;
        this.categoryID = categoryID;
        this.isCorrect = isCorrect;
        this.questionID = questionID;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }
}
