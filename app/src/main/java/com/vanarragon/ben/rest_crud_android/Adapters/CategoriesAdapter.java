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
import com.vanarragon.ben.rest_crud_android.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    //private static MyClickListener myClickListener;

    private List<Category> categories;
    private int rowLayout;
    private Context context;


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoriesLayout;
        CardView cvCategoriesLayout;
        TextView categoryID;
        TextView categoryName;


        public CategoryViewHolder(View v) {
            super(v);
            categoriesLayout = (LinearLayout) v.findViewById(R.id.categories_layout);
            cvCategoriesLayout = (CardView) v.findViewById(R.id.cv_categories_layout);

            categoryID = (TextView) v.findViewById(R.id.category_id);
            categoryName = (TextView) v.findViewById(R.id.category_name);
        }
    }

    public CategoriesAdapter(List<Category> categories, int rowLayout, Context context) {
        this.categories = categories;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //view.setOnClickListener(new MyOnClickListener());


        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        /*System.out.println("categoryID " + holder.categoryID);
        System.out.println("categoryName " + holder.categoryName);
        System.out.println("categoryID " + categories.get(position).getCategoryID().toString());
        System.out.println("categoryID " + categories.get(position).getCategoryName());*/
        holder.categoryID.setText(categories.get(position).getCategoryID().toString());
        holder.categoryName.setText(categories.get(position).getCategoryName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}