package com.vanarragon.ben.rest_crud_android.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Volley.App;

/**
 * Created by jamin on 2016-11-23.
 */

public class Base extends AppCompatActivity {

    private static final int REQUEST_FINE_LOCATION=0;

    /* Client used to interact with Google APIs. */
    public static GoogleApiClient mGoogleApiClient;
    public static boolean signedIn = false;
    public static String googleToken;
    public static String googleName;
    public static String googleMail;
    public static String userID;
    public static String firstName;
    public static String lastName;
    public static String googlePhotoURL;
    public static Bitmap googlePhoto;
    public static ProgressDialog dialog;
    public static int drawerID = 0;
    public static App app;
    public static Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = new App();
    }

    protected void goToActivity(Activity current,
                                Class<? extends Activity> activityClass,
                                Bundle bundle) {
        Intent newActivity = new Intent(current, activityClass);

        if (bundle != null) newActivity.putExtras(bundle);

        current.startActivity(newActivity);
    }

    public void openInfoDialog(Activity current) {
        Dialog dialog = new Dialog(current);
        dialog.setTitle("About Location App");
        dialog.setContentView(R.layout.info);

        TextView currentVersion = (TextView) dialog
                .findViewById(R.id.versionTextView);
        currentVersion.setText("1.0");

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("menu inflate: "+ menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void menuInfo(MenuItem m)
    {
        openInfoDialog(this);
    }

    public void menuHelp(MenuItem m)
    {

    }

    public void menuHome(MenuItem m)
    {
        goToActivity(this, MainActivity.class, null);
    }

    //you have to pass menu item item for the handler to find the menu item method
    public void logout(MenuItem item) {

        /*Plus.AccountApi.clearDefaultAccount(Base.mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(Base.mGoogleApiClient);*/
        if (Base.signedIn) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            mGoogleApiClient.disconnect();
                            Base.googleToken = "";
                            Base.signedIn = mGoogleApiClient.isConnected();
                            //mGoogleApiClient.connect();

                        }
                    });
            }

        startActivity(new Intent(Base.this, Login.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

}
