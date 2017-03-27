package com.vanarragon.ben.rest_crud_android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.Question;
import com.vanarragon.ben.rest_crud_android.Models.QuestionResponse;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Rest.ApiClient;
import com.vanarragon.ben.rest_crud_android.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class NextQuestion extends Fragment{

    private TextView lblQuestionID, lblCategoryID, lblQuestionText, lblQuestionTypeID;
    int questionID, categoryID, questionTypeID;
    String questionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_next_question, container, false);

        lblQuestionID = (TextView) v.findViewById(R.id.next_question_textview);
        lblCategoryID = (TextView) v.findViewById(R.id.next_question_textview2);
        lblQuestionTypeID = (TextView) v.findViewById(R.id.next_question_textview3);
        lblQuestionText = (TextView) v.findViewById(R.id.next_question_textview4);

        Bundle args = getArguments();

        if (args == null) {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();

        }else{
            questionID = args.getInt("qID");
            categoryID = args.getInt("cID");
            questionTypeID = args.getInt("qTID");
            questionText = args.getString("qText");

            lblQuestionID.setText(String.valueOf(questionID));
            lblCategoryID.setText(String.valueOf(categoryID));
            lblQuestionTypeID.setText(String.valueOf(questionTypeID));
            lblQuestionText.setText(questionText);
        }
        return v;
    }




}
