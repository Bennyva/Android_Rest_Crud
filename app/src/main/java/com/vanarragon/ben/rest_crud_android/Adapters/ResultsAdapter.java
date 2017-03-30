package com.vanarragon.ben.rest_crud_android.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanarragon.ben.rest_crud_android.Activities.Base;
import com.vanarragon.ben.rest_crud_android.Models.Category;
import com.vanarragon.ben.rest_crud_android.Models.Result;
import com.vanarragon.ben.rest_crud_android.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    //private static MyClickListener myClickListener;

    private List<Result> results;
    private int rowLayout;
    private Context context;



    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout resultsLayout;
        CardView cvCategoriesLayout;
        TextView resultScore,resultDateYear, resultDateTime, userName, resultNumbers;


        public ResultViewHolder(View v) {
            super(v);

            cvCategoriesLayout = (CardView) v.findViewById(R.id.cv_results_layout);

            resultScore = (TextView) v.findViewById(R.id.result_score);
            resultDateYear = (TextView) v.findViewById(R.id.result_date_year);
            resultDateTime = (TextView) v.findViewById(R.id.result_date_time);
            userName = (TextView) v.findViewById(R.id.user_name);
            resultNumbers = (TextView) v.findViewById(R.id.result_numbers);
        }
    }

    public ResultsAdapter(List<Result> results, int rowLayout, Context context) {
        this.results = results;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //view.setOnClickListener(new MyOnClickListener());






        return new ResultViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ResultViewHolder holder, final int position) {

        float score = 0;
        float correct = results.get(position).getResultScore();
        float total = results.get(position).getTotalLength();
        score = (correct * 100.0f) / total;

        String formattedScore = String.format("%.2f", score);
        holder.resultScore.setText(formattedScore+"%");


        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(results.get(position).getDateWritten());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("MMM dd yyyy, h:mma").format(date);

        holder.resultDateYear.setText(formattedDate);

        holder.userName.setText(Base.googleName);

        holder.resultNumbers.setText(results.get(position).getResultScore() + "/" + results.get(position).getTotalLength() + " -");



    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}