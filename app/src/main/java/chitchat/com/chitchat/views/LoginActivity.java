package chitchat.com.chitchat.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
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
import chitchat.com.chitchat.presenter.AuthLoginHandler;
import chitchat.com.chitchat.presenter.Contract;
import chitchat.com.chitchat.presenter.LoginPresenter;
import io.fabric.sdk.android.Fabric;

import static chitchat.com.chitchat.presenter.Contract.RC_SIGN_IN;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 11/09/16 at 15:07
 * Description: LoginActivity to authenticate User
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LoginPresenter {
    public static final String LOGINACTIVITY = LoginActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TwitterLoginButton twitterLoginButton;
    private LoginButton facebookLoginBtn;
    private SignInButton googleSignInButton;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                Contract.TWITTER_CONSUMER_KEY,
                Contract.TWITTER_SECRET);

        Fabric.with(LoginActivity.this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.loginactivity_layout);

        // initialize CallbackManager
        callbackManager = CallbackManager.Factory.create();
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
        googleSignInButton = (SignInButton) findViewById(R.id.google_signin_button);
        facebookLoginBtn = (LoginButton) findViewById(R.id.facebook_login_button);
        googleSignInButton.setOnClickListener(this);
    }

    /**Initialize logins by TwitterLogin, FacebookLogin and GoogleLogin*/
    private void initializeLogins() {
        /*handle Twitter callback*/
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                AuthLoginHandler.handleTwitterSession(result.data, firebaseAuth, LoginActivity.this);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(LOGINACTIVITY, "twitterLogin:failure", exception);
                //TODO: change display of login failure
                Toast.makeText(LoginActivity.this, "Twitter Login failure", Toast.LENGTH_SHORT).show();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /*initialize Login with Facebook*/
        facebookLoginBtn.setReadPermissions("email", "public_profile");
        facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(LOGINACTIVITY,"FacebookLoginSuccess "+ loginResult);
                //pass the token to handle with Firebase login
                if(AuthLoginHandler.handleFacebookLoginToken(loginResult.getAccessToken(),firebaseAuth, LoginActivity.this)){
                    /*if successful start the MainActivity*/
                    MainActivity.start(LoginActivity.this);
                }else{
                    displayError();
                }
            }

            @Override
            public void onCancel() {
                Log.d(LOGINACTIVITY, "FacebookLoginCancel:");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(LOGINACTIVITY, "FacebookLoginFailure "+error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // result returned from launching the intent
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(googleSignInResult.isSuccess()){
                //Google sign in was successful authenticate with Firebase
                GoogleSignInAccount account = googleSignInResult.getSignInAccount();
                AuthLoginHandler.AuthGoogleWithFirebase(account, firebaseAuth, LoginActivity.this);
            }else{
                //Google sign in failed
                //TODO; Update user appropriately
                Toast.makeText(this, "Google Sign in failed", Toast.LENGTH_SHORT).show();
            }

        }
        // pass the activity result to the twitter button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        //pass the request code, result and data to the callback manager to handle Facebook login
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(LOGINACTIVITY, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.google_signin_button:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void displayError() {
        Toast.makeText(this, "Login with Facebook Failed", Toast.LENGTH_SHORT).show();
    }
}
