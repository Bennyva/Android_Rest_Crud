package com.vanarragon.ben.rest_crud_android.Rest;

/**
 * Created by Benjamin van Arragon on 2017-02-21.
 */

import com.vanarragon.ben.rest_crud_android.Models.AnswerResponse;
import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.QuestionResponse;
import com.vanarragon.ben.rest_crud_android.Models.User;
import com.vanarragon.ben.rest_crud_android.Models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //users stuff
    @GET("users")
    Call<UserResponse> retrieveUsers();

    @GET("users/{id}")
    Call<UserResponse> retrieveSpecificUser(@Path("id") int id);

    //fyp stuff
    @GET("categories")
    Call<CategoryResponse> retrieveCategories();

    @GET("questions")
    Call<QuestionResponse> retrieveQuestions();

    @GET("answers")
    Call<AnswerResponse> retrieveAnswers();

    @GET("questions/{category_id}")
    Call<QuestionResponse> retrieveQuestionsPerCategory(@Path("category_id") int category_id);

    @GET("answers/{category_id}")
    Call<AnswerResponse> retrieveAnswersPerCategory(@Path("category_id") int category_id);

}
