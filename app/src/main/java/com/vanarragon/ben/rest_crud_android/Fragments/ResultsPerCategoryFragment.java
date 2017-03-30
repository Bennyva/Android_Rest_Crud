package com.vanarragon.ben.rest_crud_android.Fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import com.vanarragon.ben.rest_crud_android.Adapters.CategoriesAdapter;
import com.vanarragon.ben.rest_crud_android.Adapters.RecyclerItemClickListener;
import com.vanarragon.ben.rest_crud_android.Adapters.ResultsAdapter;
import com.vanarragon.ben.rest_crud_android.Models.Category;
import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.Result;
import com.vanarragon.ben.rest_crud_android.Models.ResultResponse;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Rest.ApiClient;
import com.vanarragon.ben.rest_crud_android.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_categories;
import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_results;

/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class ResultsPerCategoryFragment extends Fragment{

    int category_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //initialize the category id variable and define the arrays to hold the data from the enqueue
        Bundle bundle = this.getArguments();
        if(bundle != null){
            category_id = bundle.getInt("ID");
        }

        Call<ResultResponse> call = apiInterface.retrieveResultsPerCategory(category_id);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                int statusCode = response.code();
                //List<User> users = response.body().getUsers();
                final List<Result> results = response.body().getResults();

                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.categories_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //change the adapter to using a cardview or just a regular list view underneath this comment by changing the R.layout.cardview_user with $_old_list_item_users.xmls.xml
                recyclerView.setAdapter(new ResultsAdapter(results, cardview_results, getActivity().getApplicationContext()));
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                //Toast.makeText(getActivity(),"Training in " + results.get(position).getCategoryName(),Toast.LENGTH_LONG).show();
                                //openResults(position, categories);

                            }

                            @Override public void onLongItemClick(View view, final int position) {
                                // do whatever
                                //Toast.makeText(getActivity(),"Hi, long press" + position,Toast.LENGTH_LONG).show();

                                //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                //        getActivity());

                                // set title
                                //alertDialogBuilder.setTitle("More Info on " + categories.get(position).getCategoryName());

                                // set dialog message
                                /*alertDialogBuilder
                                        .setMessage("Description: "+ "\n\n" + categories.get(position).getCategoryDescription()+ "\n\n" +" Would you like to continue training?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, close
                                                openResults(position, categories);
                                            }
                                        })
                                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.cancel();
                                            }
                                        });
                                */
                                // create alert dialog
                                //AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                //alertDialog.show();
                            }
                        })
                );


            }
            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR: ", t.toString());
            }
        });

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }

    private void openResults(int position, List<Category> categories){
        //open new fragment
        TestNextQuestion testNextQuestion = new TestNextQuestion();
        // Our shared element (in Fragment A)
        CardView view2   = (CardView) getView().findViewById(R.id.cv_categories_layout);

        //animation stuff
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementReturnTransition(TransitionInflater.from(
                    getActivity()).inflateTransition(R.transition.expand_cardview_trans));
            setExitTransition(TransitionInflater.from(
                    getActivity()).inflateTransition(android.R.transition.no_transition));

            testNextQuestion.setSharedElementEnterTransition(TransitionInflater.from(
                    getActivity()).inflateTransition(R.transition.expand_cardview_trans));
            testNextQuestion.setEnterTransition(TransitionInflater.from(
                    getActivity()).inflateTransition(android.R.transition.no_transition));
        }

        //passing bundle info
        //TODO find how to pass data from the view object
        String catName = categories.get(position).getCategoryName();
        int catID = categories.get(position).getCategoryID();

        Bundle bundle = new Bundle();
        bundle.putInt("ID", catID);
        bundle.putString("CatName", catName);

        testNextQuestion.setArguments(bundle);

        //transition fragments
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addSharedElement(view2, "userViewTransition");
        transaction.replace(R.id.fragment_container, testNextQuestion);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
