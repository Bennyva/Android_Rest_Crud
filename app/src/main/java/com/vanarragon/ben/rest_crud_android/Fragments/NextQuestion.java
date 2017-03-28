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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.vanarragon.ben.rest_crud_android.Adapters.CategoriesAdapter;
import com.vanarragon.ben.rest_crud_android.Adapters.RecyclerItemClickListener;
import com.vanarragon.ben.rest_crud_android.Adapters.UsersAdapter;
import com.vanarragon.ben.rest_crud_android.Models.Answer;
import com.vanarragon.ben.rest_crud_android.Models.AnswerResponse;
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

public class NextQuestion extends Fragment{

    private TextView lblQuestionID, lblCategoryID, lblQuestionText, lblQuestionTypeID;
    private RadioButton rb1,rb2,rb3,rb4;
    private RadioGroup rg;
    private Button btnNextQuestion, btnStart;
    int questionID, categoryID, questionTypeID;
    String questionText;
    int i = 0;
    int category_id = 0;
    ApiInterface apiInterface;
    List<Question> questionsArray;
    List<Answer> answersArray;
    Integer correctAnswerID;
    String correctAnswerText;
    Integer userInputID;
    boolean nextQuestion = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_next_question, container, false);

/*        lblQuestionID = (TextView) v.findViewById(R.id.next_question_textview);
        lblCategoryID = (TextView) v.findViewById(R.id.next_question_textview2);
        lblQuestionTypeID = (TextView) v.findViewById(R.id.next_question_textview3);*/
        lblQuestionText = (TextView) v.findViewById(R.id.next_question_textview4);
        rg = (RadioGroup) v.findViewById(R.id.rgAnswers);
/*        rb1 = (RadioButton) v.findViewById(R.id.rb1);
        rb2 = (RadioButton) v.findViewById(R.id.rb2);
        rb3 = (RadioButton) v.findViewById(R.id.rb3);
        rb4 = (RadioButton) v.findViewById(R.id.rb4);*/


        btnStart = (Button) v.findViewById(R.id.btnStart);
        btnNextQuestion = (Button) v.findViewById(R.id.btnNextQuestion);

        //initialize the category id variable and define the arrays to hold the data from the enqueue



        Bundle bundle = this.getArguments();
        if(bundle != null){
            category_id = bundle.getInt("ID");
        }

        //set up the http request to pull questions and answers per category_id
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final Call<AnswerResponse> answerCall = apiInterface.retrieveAnswersPerCategory(category_id);
        answerCall.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> answerResponse) {

                    final List<Answer> answers = answerResponse.body().getAnswers();
                    answersArray = answers;
                    for(int i = 0; i < answersArray.size(); i++) {
                        /*System.out.println(answersArray.get(i).getAnswerText());
                        System.out.println(answersArray.get(i).getCategoryID());*/
                    }

            }
            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });


        Call<QuestionResponse> call = apiInterface.retrieveQuestionsPerCategory(category_id);

        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                //==========old stuff went here
                //===================================== new stuff here
                int statusCode = response.code();
                //List<User> users = response.body().getUsers();
                final List<Question> questions = response.body().getQuestions();



                //gets buttons click
                btnNextQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnNextQuestion.setEnabled(false);
                        rg.removeAllViews();
                        checkUserInput();
                        /*try {
                            Thread.sleep(2000);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }*/


                        //sets the title after the first iteration and populates each quesitons with the data pulled from the questions array above
                        if(i == 0 || i < questions.size()){
                            btnNextQuestion.setText("Next Question");
                            int questionID = questions.get(i).getQuestionID();
                            int categoryID = questions.get(i).getCategoryID();
                            String questionText = questions.get(i).getQuestionText();
                            int questionTypeID = questions.get(i).getQuestionTypeID();

                            correctAnswerID = 0;

                            for(int j = 0; j < answersArray.size(); j++){
                                if(answersArray.get(j).getQuestionID() == questionID){
                                    if(answersArray.get(j).getIsCorrect() == 1){
                                        correctAnswerID = answersArray.get(j).getAnswerID();
                                        correctAnswerText = answersArray.get(j).getAnswerText();
                                    }
                                    RadioButton rb = new RadioButton(getActivity());

                                    rb.setId(answersArray.get(j).getAnswerID());
                                    //System.out.println(j);
                                    rb.setText(answersArray.get(j).getAnswerText());
                                    rb.setPadding(35,40,0,40);
                                    rg.setPadding(35,0,0,0);
                                    rg.addView(rb);
                                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                            btnNextQuestion.setEnabled(true);
                                            userInputID = i;
                                            System.out.println(i);
                                            System.out.println(correctAnswerID);
                                           /* int selectedId = rg.getCheckedRadioButtonId();
                                            System.out.println(selectedId + " " + i);*/
                                            //=====
                                            //SELECTED ID IS THE SAME AS I IN THIS CASE, I IS THE USERS INPUT
                                            //=====


                                        }
                                    });
                                }
                            }





                                /*lblQuestionID.setText(String.valueOf(questionID));
                                lblCategoryID.setText(String.valueOf(categoryID));
                                lblQuestionTypeID.setText(String.valueOf(questionTypeID));*/
                            lblQuestionText.setText((i + 1) + ": " + questionText);
                            i++;

                            if(i == questions.size()){
                                btnNextQuestion.setText("Finish");
                            }
                            //once its looped through the array of questions it will trigger this method, results or menu?
                        }else if(i == questions.size()){
                            nextQuestion = true;
                            MenuFragment menuFragment = new MenuFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, menuFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                    }
                });

                /*btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnStart.setVisibility(View.GONE);
                        btnNextQuestion.setVisibility(View.VISIBLE);
                        lblQuestionText.setText((i + 1) + ": " + questionText);
                        i++;

                    }
                });*/

            }
            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });
        return v;
    }

    private void checkUserInput(){
        if(userInputID != null && correctAnswerID != null){
            if(userInputID == correctAnswerID){
                System.out.println("Correct Answer!");
                // Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
                StyleableToast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT, R.style.SuccessToast).show();

            }else{
                StyleableToast.makeText(getActivity(), "Incorrect. Correct answer was " + correctAnswerText, Toast.LENGTH_SHORT, R.style.WrongToast).show();

            }
        }
    }




}
