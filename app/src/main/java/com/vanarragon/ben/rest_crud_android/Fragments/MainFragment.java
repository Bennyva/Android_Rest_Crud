package com.vanarragon.ben.rest_crud_android.Fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vanarragon.ben.rest_crud_android.Adapters.RecyclerItemClickListener;
import com.vanarragon.ben.rest_crud_android.Adapters.UsersAdapter;
import com.vanarragon.ben.rest_crud_android.Models.User;
import com.vanarragon.ben.rest_crud_android.Models.UserResponse;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Rest.ApiClient;
import com.vanarragon.ben.rest_crud_android.Rest.ApiInterface;

import android.support.v4.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class MainFragment extends Fragment{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiInterface.retrieveUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int statusCode = response.code();
                List<User> users = response.body().getUsers();

                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.users_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new UsersAdapter(users, R.layout.list_item_users, getActivity().getApplicationContext()));
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                Toast.makeText(getActivity(),"Hi, short press" + position,Toast.LENGTH_LONG).show();

                            }

                            @Override public void onLongItemClick(View view, int position) {
                                // do whatever
                                Toast.makeText(getActivity(),"Hi, long press" + position,Toast.LENGTH_LONG).show();
                            }
                        })
                );


            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;





    }


}
