package com.vanarragon.ben.rest_crud_android.Fragments;



import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vanarragon.ben.rest_crud_android.Activities.MainActivity;
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

import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_users;

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
                //change the adapter to using a cardview or just a regular list view underneath this comment by changing the R.layout.cardview_user with list_item_users.xml
                recyclerView.setAdapter(new UsersAdapter(users, cardview_users, getActivity().getApplicationContext()));
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                Toast.makeText(getActivity(),"Hi, short press" + position,Toast.LENGTH_LONG).show();

                                //open new fragment
                                DetailedView detailedView= new DetailedView();
                                // Our shared element (in Fragment A)
                                CardView view2   = (CardView) getView().findViewById(R.id.cv_users_layout);

                                //animation stuff
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    setSharedElementReturnTransition(TransitionInflater.from(
                                            getActivity()).inflateTransition(R.transition.expand_cardview_trans));
                                    setExitTransition(TransitionInflater.from(
                                            getActivity()).inflateTransition(android.R.transition.no_transition));

                                    detailedView.setSharedElementEnterTransition(TransitionInflater.from(
                                            getActivity()).inflateTransition(R.transition.expand_cardview_trans));
                                    detailedView.setEnterTransition(TransitionInflater.from(
                                            getActivity()).inflateTransition(android.R.transition.no_transition));
                                }

                                //passing bundle info
                                //TODO find how to pass data from the view object
                                Bundle bundle = new Bundle();
                                bundle.putInt("ID", position);
                                detailedView.setArguments(bundle);

                                //transition fragments
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.addSharedElement(view2, "userViewTransition");
                                transaction.replace(R.id.fragment_container, detailedView);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                            @Override public void onLongItemClick(View view, int position) {
                                // do whatever
                                Toast.makeText(getActivity(),"Hi, long press" + position,Toast.LENGTH_LONG).show();

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        getActivity());

                                // set title
                                alertDialogBuilder.setTitle("Your Title");

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Click yes to exit!")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, close
                                                // current activity
                                                getActivity().finish();
                                            }
                                        })
                                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.cancel();
                                            }
                                        });

                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();
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
