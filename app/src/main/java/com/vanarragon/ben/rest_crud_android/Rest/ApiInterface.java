package com.vanarragon.ben.rest_crud_android.Rest;

/**
 * Created by Benjamin van Arragon on 2017-02-21.
 */

import android.database.Observable;

import com.vanarragon.ben.rest_crud_android.Models.AnswerResponse;
import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.QuestionResponse;
import com.vanarragon.ben.rest_crud_android.Models.ResultResponse;
import com.vanarragon.ben.rest_crud_android.Models.User;
import com.vanarragon.ben.rest_crud_android.Models.UserResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("users")
    Call<Void> createUser(@Field("userID") String userID,
                    @Field("userEmail") String userEmail,
                    @Field("lastLogIn") String lastLogIn,
                    @Field("logInCount") String logInCount,
                    @Field("userFirstName") String userFirstName,
                    @Field("userLastName") String userLastName);

    @FormUrlEncoded
    @PUT("users")
    Call<Void> updateUser(@Field("lastLogIn") String lastLogIn,
                          @Field("logInCount") int logInCount,
                          @Field("userEmail") String userEmail);

    @FormUrlEncoded
    @POST("results")
    Call<Void> insertResult(@Field("resultScore") int resultScore,
                          @Field("userID") String userID,
                          @Field("categoryID") int categoryID,
                          @Field("totalLength") int totalLength,
                          @Field("dateWritten") String dateWritten);

    @GET("results/{category_id}")
    Call<ResultResponse> retrieveResultsPerCategory(@Path("category_id") int category_id);
}
