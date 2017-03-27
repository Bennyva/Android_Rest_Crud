package com.vanarragon.ben.rest_crud_android.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.vanarragon.ben.rest_crud_android.Adapters.CategoriesAdapter;
import com.vanarragon.ben.rest_crud_android.Adapters.RecyclerItemClickListener;
import com.vanarragon.ben.rest_crud_android.Adapters.UsersAdapter;
import com.vanarragon.ben.rest_crud_android.Models.Category;
import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.Question;
import com.vanarragon.ben.rest_crud_android.Models.QuestionResponse;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Rest.ApiClient;
import com.vanarragon.ben.rest_crud_android.Rest.ApiInterface;
import com.vanarragon.ben.rest_crud_android.Models.QuestionResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_categories;
import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_users;


/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class DetailedView extends Fragment{

    private TextView lblQuestionID, lblCategoryID, lblQuestionText, lblQuestionTypeID;
    private Button btnNextQuestion;
    int questionID, categoryID, questionTypeID;
    String questionText;
    int i = 0;

    boolean nextQuestion = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_next_question, container, false);

        lblQuestionID = (TextView) v.findViewById(R.id.next_question_textview);
        lblCategoryID = (TextView) v.findViewById(R.id.next_question_textview2);
        lblQuestionTypeID = (TextView) v.findViewById(R.id.next_question_textview3);
        lblQuestionText = (TextView) v.findViewById(R.id.next_question_textview4);

        btnNextQuestion = (Button) v.findViewById(R.id.btnNextQuestion);


        int category_id = 0;
        List<Question> questionsArray;

        Bundle bundle = this.getArguments();
        if(bundle != null){
            category_id = bundle.getInt("ID");
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<QuestionResponse> call = apiInterface.retrieveQuestionsPerCategory(category_id);
        call.enqueue(new Callback<QuestionResponse>() {


            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                int statusCode = response.code();
                //List<User> users = response.body().getUsers();
                final List<Question> questions = response.body().getQuestions();
                    //gets buttons click
                    btnNextQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //sets the title after the first iteration and populates each quesitons with the data pulled from the questions array above
                            if(i == 0 || i < questions.size()){
                                btnNextQuestion.setText("Next Question");

                                int questionID = questions.get(i).getQuestionID();
                                int categoryID = questions.get(i).getCategoryID();
                                String questionText = questions.get(i).getQuestionText();
                                int questionTypeID = questions.get(i).getQuestionTypeID();

                                lblQuestionID.setText(String.valueOf(questionID));
                                lblCategoryID.setText(String.valueOf(categoryID));
                                lblQuestionTypeID.setText(String.valueOf(questionTypeID));
                                lblQuestionText.setText(questionText);


                                i++;

                                if(i == questions.size()){
                                    btnNextQuestion.setText("Finish");
                                }
                                //once its looped through the array of questions it will trigger this method, results or menu?
                            }else if(i == questions.size()){
                                nextQuestion = true;

                                MenuFragment menuFragment = new MenuFragment();

                                //transition fragments
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                //transaction.addSharedElement(view2, "userViewTransition");
                                transaction.replace(R.id.fragment_container, menuFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            }

                        }
                    });
                    //open new fragment
                /*NextQuestion nextQuestion = new NextQuestion();

                //transition fragments
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //transaction.addSharedElement(view2, "userViewTransition");
                transaction.replace(R.id.fragment_container, nextQuestion);
                transaction.addToBackStack(null);
                transaction.commit();*/

            }
            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });
        return v;
    }


}
