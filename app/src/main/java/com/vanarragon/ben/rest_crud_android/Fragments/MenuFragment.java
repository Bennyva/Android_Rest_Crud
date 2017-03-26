package com.vanarragon.ben.rest_crud_android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vanarragon.ben.rest_crud_android.Activities.Base;
import com.vanarragon.ben.rest_crud_android.Activities.Login;
import com.vanarragon.ben.rest_crud_android.R;

/**
 * Created by Benjamin van Arragon on 2017-02-28.
 */

public class MenuFragment extends Fragment{

    //declare variables
    private Button btnTraining, btnTesting, btnResults, btnSettings;
    FragmentTransaction transaction;
    TrainingFragment trainingFragment;


    //view for fragment
    View myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_menu, container, false);

        btnTraining = (Button) myView.findViewById(R.id.btnTraining);
        btnTesting = (Button) myView.findViewById(R.id.btnTesting);
        btnResults = (Button) myView.findViewById(R.id.btnResults);
        btnSettings = (Button) myView.findViewById(R.id.btnSettings);

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                transaction = fm.beginTransaction();
                trainingFragment = new TrainingFragment();
                transaction.replace(R.id.fragment_container, trainingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return myView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}

