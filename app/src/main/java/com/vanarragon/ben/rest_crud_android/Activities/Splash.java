package com.vanarragon.ben.rest_crud_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.vanarragon.ben.rest_crud_android.R;

/**
 * Created by jamin on 2016-11-23.
 */
public class Splash extends Activity {
    // used to know if the back button was pressed in the splash screen activity
    // and avoid opening the next activity
    private boolean 			mIsBackButtonPressed;
    private static final int 	SPLASH_DURATION = 3000; // 2 seconds

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // make sure we close the splash screen so the user
                // won't come back when it presses back key
                finish();

                if (!mIsBackButtonPressed) {
                    // start the home screen if the back button wasn't pressed already
                    //type Login to start login after splash screen
                    Intent intent = new Intent(Splash.this, Login.class);
                    Splash.this.startActivity(intent);
                }
            }
        }, SPLASH_DURATION); // time in milliseconds to delay call to run()
    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
}

