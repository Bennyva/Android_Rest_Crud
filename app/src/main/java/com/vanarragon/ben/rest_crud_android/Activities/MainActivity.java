package com.vanarragon.ben.rest_crud_android.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.vanarragon.ben.rest_crud_android.Fragments.MainFragment;
import com.vanarragon.ben.rest_crud_android.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment mainFragment = new MainFragment();

        //removes views from layout beneath
        //((RelativeLayout)findViewById(R.id.fragment_container)).removeAllViews();

        transaction.replace(R.id.fragment_container, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
