package chitchat.com.chitchat.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

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

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Contract.TWITTER_CONSUMER_KEY, Contract.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.loginactivity_layout);

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
        }
    }

}
