package chitchat.com.chitchat.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.presenter.Contract;
import io.fabric.sdk.android.Fabric;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 11/09/16 at 15:07
 * <p>
 * Description: LoginActivity to authenticate User
 */

public class LoginActivity extends AppCompatActivity {
    private static final String LOGINACTIVITY = LoginActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TwitterLoginButton twitterLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Contract.TWITTER_CONSUMER_KEY, Contract.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.loginactivity_layout);
        initViews();
        initializeLogins();
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //User is signed in, start main activity
                    String userData = user.getDisplayName() + " "+ user.getEmail()+ " "+ user.getUid();
                    Log.d(LOGINACTIVITY, "AuthStateChanged:SignedIn" +userData);
                    Intent openMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(openMain);
                }else{
                    // user is signed out
                    Log.d(LOGINACTIVITY, "AuthStateChanged:signedOut:");
                }
            }
        };
    }


    /**Initialize UI Controls*/
    private void initViews() {
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    }

    /**Initialize logins by TwitterLogin, FacebookLogin and GoogleLogin*/
    private void initializeLogins() {
        /*handle Twitter callback*/
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(LOGINACTIVITY, "twitterLogin:failure", exception);
                //TODO: change display of login failure
                Toast.makeText(LoginActivity.this, "Twitter Login failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // pass the activity result to the twitter button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
