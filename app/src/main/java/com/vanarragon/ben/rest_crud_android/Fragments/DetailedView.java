package com.vanarragon.ben.rest_crud_android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanarragon.ben.rest_crud_android.Adapters.UsersAdapter;
import com.vanarragon.ben.rest_crud_android.R;


import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_users;


/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class DetailedView extends Fragment{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Bundle bundle = getArguments();

        if (bundle != null) {
            int id = bundle.getInt("ID");
        }

        View v = inflater.inflate(R.layout.fragment_detailed, container, false);
        return v;





    }




}
