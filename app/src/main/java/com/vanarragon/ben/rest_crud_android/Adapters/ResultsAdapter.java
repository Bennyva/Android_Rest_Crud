package com.vanarragon.ben.rest_crud_android.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanarragon.ben.rest_crud_android.Models.Category;
import com.vanarragon.ben.rest_crud_android.Models.Result;
import com.vanarragon.ben.rest_crud_android.R;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    //private static MyClickListener myClickListener;

    private List<Result> results;
    private int rowLayout;
    private Context context;


    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout resultsLayout;
        CardView cvCategoriesLayout;
        TextView resultID,resultDate;


        public ResultViewHolder(View v) {
            super(v);

            cvCategoriesLayout = (CardView) v.findViewById(R.id.cv_results_layout);

            resultID = (TextView) v.findViewById(R.id.result_id);
            resultDate = (TextView) v.findViewById(R.id.result_date);
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
        /*System.out.println("categoryID " + holder.categoryID);
        System.out.println("categoryName " + holder.categoryName);
        System.out.println("categoryID " + categories.get(position).getCategoryID().toString());
        System.out.println("categoryID " + categories.get(position).getCategoryName());*/
        holder.resultID.setText(results.get(position).getResultID().toString());
        holder.resultDate.setText(results.get(position).getDateWritten());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}