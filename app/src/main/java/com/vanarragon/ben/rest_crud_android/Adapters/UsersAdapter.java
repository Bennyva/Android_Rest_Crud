package com.vanarragon.ben.rest_crud_android.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.vanarragon.ben.rest_crud_android.Models.User;
import com.vanarragon.ben.rest_crud_android.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    //private static MyClickListener myClickListener;

    private List<User> users;
    private int rowLayout;
    private Context context;


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        LinearLayout usersLayout;
        CardView cvUsersLayout;
        TextView userID;
        TextView userEmail;
        TextView userPassword;
        TextView userJoinDate;


        public UserViewHolder(View v) {
            super(v);
            usersLayout = (LinearLayout) v.findViewById(R.id.users_layout);
            cvUsersLayout = (CardView) v.findViewById(R.id.cv_users_layout);

            userID = (TextView) v.findViewById(R.id.user_id);
            userEmail = (TextView) v.findViewById(R.id.user_email);
            userPassword = (TextView) v.findViewById(R.id.user_password);
            userJoinDate = (TextView) v.findViewById(R.id.user_join_date);
        }
    }

    public UsersAdapter(List<User> users, int rowLayout, Context context) {
        this.users = users;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //view.setOnClickListener(new MyOnClickListener());


        return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.userID.setText(users.get(position).getUserID());
       /* holder.userEmail.setText(users.get(position).getUserEmail());
        holder.userPassword.setText(users.get(position).getUserPassword());
        holder.userJoinDate.setText(users.get(position).getUserJoinDate().toString());*/
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}