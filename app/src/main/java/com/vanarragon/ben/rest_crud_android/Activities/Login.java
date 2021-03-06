package com.vanarragon.ben.rest_crud_android.Activities;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.vanarragon.ben.rest_crud_android.Adapters.CategoriesAdapter;
import com.vanarragon.ben.rest_crud_android.Adapters.RecyclerItemClickListener;
import com.vanarragon.ben.rest_crud_android.Fragments.NextQuestion;
import com.vanarragon.ben.rest_crud_android.Models.Category;
import com.vanarragon.ben.rest_crud_android.Models.CategoryResponse;
import com.vanarragon.ben.rest_crud_android.Models.User;
import com.vanarragon.ben.rest_crud_android.Models.UserResponse;
import com.vanarragon.ben.rest_crud_android.R;
import com.vanarragon.ben.rest_crud_android.Rest.ApiClient;
import com.vanarragon.ben.rest_crud_android.Rest.ApiInterface;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.vanarragon.ben.rest_crud_android.R.layout.cardview_categories;

//DONT CALL ON STOP HERE IT BREAKS THE APPLICATION SO MUCH, SPENT 24 HOURS STRAIGHT TRYING TO FIX THAT STUPID BUG...
//I WILL REMEMBER THIS NIGHT! UP UNTIL 9:30AM DEBUGGING, SLEPT IN UNTIL 6PM, AND SOLVED IT AT 9:00...that'll go down in my history
//books for coding :( do not have an on stop callback...please...do not!
public class Login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    /* Request code used to invoke sign in

     user interactions. */



    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    //permissions stuff
    private static final int PERMISSION_REQUEST_CODE=0;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    //Image Loader
    private ImageLoader imageLoader;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    List<User> users;
    String userIDDB, userEmailDB, userFirstNameDB, userLastNameDB;
    int logInCountDB;
    String lastLogInDB;

    String userFirstName, userLastName, currentLogIn;


    //video background
    VideoView videoView;
    ArrayList<Uri> uri = new ArrayList<Uri>();
    File dir;


/*    @Override
    protected void onStop() {
        super.onStop();
        Base.mGoogleApiClient.disconnect();
        Log.d("On Stop: ", " ye");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        videoView = (VideoView) findViewById(R.id.videoView);

        //ADD ANY VIDEOS HERE, THEY WILL BE RANDOMLY SHOWN ON START THROUGH GET RANDOM () METHOD
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background2));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background3));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background4));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background5));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background6));
        uri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_background7));


        //Video Loop
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.start(); //need to make transition seamless.
            }
        });

        videoView.setDrawingCacheEnabled(true);
        videoView.setVideoURI(getRandom(uri));
        videoView.requestFocus();
        videoView.start();

        // Views
        //mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);



        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        Base.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                /*.enableAutoManage(this *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)*/
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();
        // [END build_client]


        
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);


        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION,0);
        loadPermissions(Manifest.permission.READ_CONTACTS,123);
        loadPermissions(Manifest.permission.GET_ACCOUNTS,123);


    }

    //for picking random video background
    public static Uri getRandom(List<Uri> uriArrayList) {
        int rnd = new Random().nextInt(uriArrayList.size());
        return uriArrayList.get(rnd);
    }


  /*  @Override
    protected void onPause() {
        super.onPause();
    if (Base.mGoogleApiClient.isConnected()){
        Base.mGoogleApiClient.disconnect();
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Base.mGoogleApiClient.connect();
    }*/

    @Override
    public void onStart() {
        super.onStart();
        //connect to the api
        Base.mGoogleApiClient.connect();


        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(Base.mGoogleApiClient);
        /*if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            //GoogleSignInResult result = opr.get();
            //handleSignInResult(result);
        } else {*/
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        //}

    }



    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]


    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            Log.d(TAG, "handleSignInResult2:" + result.isSuccess());

            loadPermissions(Manifest.permission.GET_ACCOUNTS,PERMISSION_REQUEST_CODE);
            if(!checkPermission()){
                requestPermission();
            Log.d(TAG, "request permissions:" + result.isSuccess());
            }
            else{
                Base.googleToken = acct.getIdToken();
                Log.d(TAG, "else :" + result.isSuccess());
                //Base.mGoogleApiClient.connect();
                Log.d(TAG, "connected:" + Base.mGoogleApiClient.isConnected());
                //Base.signedIn = Base.mGoogleApiClient.isConnected();
                if(!checkPermission())
                    requestPermission();
                else {
                    //sets the last location before any views are opened on the device, this is so it can compare the current location to other posts
                    //in the list view that is auto generated
                    //Base.lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(Base.mGoogleApiClient);
                }


                Base.googleName = acct.getDisplayName();
                userFirstName = acct.getGivenName();
                userLastName = acct.getFamilyName();

                Base.firstName = userFirstName;
                Base.lastName = userLastName;

                Date nowDate = new Date();
                System.out.println(nowDate);
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                currentLogIn = sdf.format(nowDate);
                //System.out.println(userFirstName);
                //System.out.println(userLastName);
                Base.googleMail = acct.getEmail();


                // ============
                // SELECT * FROM USERS DATABASE, LOOP THROUGH TO CHECK IF OUR USER EMAIL ALREADY EXISTS

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<UserResponse> call = apiInterface.retrieveUsers();
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        int statusCode = response.code();
                        users = response.body().getUsers();
                        //users.get(i).getUserEmail().equals(Base.googleMail)
                        for(int i = 0; i < users.size(); i++){
                            int j = users.get(i).getUserEmail().indexOf(Base.googleMail);
                            if(users.get(i).getUserEmail().equals(Base.googleMail)){
                                System.out.println("Welcome back " + Base.googleMail);
                                Base.userID = users.get(i).getUserID();
                                userEmailDB = users.get(i).getUserEmail();
                                lastLogInDB = users.get(i).getLastLogIn();
                                logInCountDB = users.get(i).getLogInCount();
                                try {
                                    updateLogIn(lastLogInDB, logInCountDB, userEmailDB);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                System.out.println("Welcome new user " + Base.googleMail);
                                insertNewUser();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("ERROR: ", t.toString());
                    }
                });


                //Base.googlePhotoURL = acct.getPhotoUrl().toString();
                // Show a message to the user that we are signing in.
                Toast.makeText(this, "Signing in " + Base.googleName, Toast.LENGTH_LONG).show();
                startMainActivity();
            }



        } else {
            // Signed out, show unauthenticated UI.
            //Toast.makeText(this, "Error signing in.", Toast.LENGTH_LONG).show();
            //updateUI(false);
        }
    }
    // [END handleSignInResult]

    private void insertNewUser() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Void> newUser = apiInterface.createUser("3",Base.googleMail,currentLogIn,"1",userFirstName,userLastName);
        newUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("new User inserted success");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("new User insert FAILED");
            }
        });
    }

    private void updateLogIn(String lastLogInDB, int logInCountDB, String userEmailDB) throws ParseException {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        //Date date = format.parse(lastLogInDB);
        logInCountDB += 1;

        Date nowDate = new Date();
        System.out.println(nowDate);
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentLogIn = sdf.format(nowDate);

        Call<Void> updateUser = apiInterface.updateUser(currentLogIn,logInCountDB,userEmailDB);
        updateUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("User update inserted success");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("User update insert FAILED");
            }
        });


    }


    public void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }


    // [START signIn]
    private void signIn() {
        System.out.println("Starting sign in method");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Base.mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]


    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(Base.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }



    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            //mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    //start main activity
    private void startMainActivity() {

        Intent intent = new Intent(Login.this, MainActivity.class);
        Login.this.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:

                signIn();
                break;
            //case R.id.sign_out_button:
                //signOut();
                //break;
            case R.id.disconnect_button:
                revokeAccess();
                System.out.println("Disconnecting Account");
                break;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("OnConnected:", " connected!");
        Base.signedIn = Base.mGoogleApiClient.isConnected();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        }
        //Base.lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(Base.mGoogleApiClient);
        //Log.d(TAG, "connectedcallback:" + Base.mGoogleApiClient.isConnected());
        //Log.d(TAG, "location:" + Base.lastKnownLocation.getLatitude() + ", " + Base.lastKnownLocation.getLongitude());

    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.d("df","");
    }

    //https://ddrohan.github.io/mad-2016/topic04-google-services/talk-3-google-3/01.maps.pdf
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void loadPermissions(String perm, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm}, requestCode);
            }
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                    Toast.makeText(this,"Permissions Granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    // no granted
                }
                return;
            }

        }

    }


}
