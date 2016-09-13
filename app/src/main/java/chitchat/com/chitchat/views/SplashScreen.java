package chitchat.com.chitchat.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import chitchat.com.chitchat.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 07/09/16 at 17:11
 * Description:
 */

public class SplashScreen extends AppCompatActivity {

    /*FIELDS*/
    private static final String SPLASH_SCREEN_TAG = SplashScreen.class.getSimpleName();
    private TextView appName;
    private ImageView appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen_layout);

        initializeUICtrls();

        //set the timer
        Thread timer = new Thread(){
            @Override
            public void run(){
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                //create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                //if activity has never started before

                if(isFirstStart){
                    //launch this activity
                    Intent intent = new Intent(SplashScreen.this, AppIntroduction.class);
                    startActivity(intent);

                    //make a new shared preferences editor
                    SharedPreferences.Editor editor = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    editor.putBoolean("firstStart", false);

                    //apply the changes
                    editor.apply();
                }else{
                    try{
                        sleep(2000);
                        Intent openLogin = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(openLogin);
                    }catch(InterruptedException ie){
                        ie.printStackTrace();
                        Log.d(SPLASH_SCREEN_TAG, ie.toString());
                    }
                }
            }
        };
        //start timer
        timer.start();
    }

    /**Initializes the User Interface controls
     * obtains the appName, appTag and appIcon variables and finds their ids in the layout*/
    private void initializeUICtrls() {
        //appIcon = (ImageView)findViewById(R.id.appicon_splash);
        //appName = (TextView)findViewById(R.id.appname_splash);
    }

    /*kill this splash screen to save memory*/
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}