package com.vanarragon.ben.rest_crud_android.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vanarragon.ben.rest_crud_android.Fragments.MenuFragment;
import com.vanarragon.ben.rest_crud_android.R;

import static com.vanarragon.ben.rest_crud_android.Activities.Base.mGoogleApiClient;


public class MainActivity extends Base {

    FragmentTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        dialog = new ProgressDialog(this,1);

        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();

        //TrainingFragment mainFragment = new TrainingFragment();
        MenuFragment menuFragment = new MenuFragment();

        //removes views from layout beneath
        //((RelativeLayout)findViewById(R.id.fragment_container)).removeAllViews();

        transaction.replace(R.id.fragment_container, menuFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.menu_info:
                // info action
                System.out.println("menu info hit");
                return true;
            case R.id.menu_logout:
                // logout action
                System.out.println("logout hit");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
