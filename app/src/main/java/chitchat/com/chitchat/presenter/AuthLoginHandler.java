package chitchat.com.chitchat.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.TwitterSession;

import chitchat.com.chitchat.views.LoginActivity;
import chitchat.com.chitchat.views.main.MainActivity;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter
 * Created by lusinabrian on 11/09/16 at 16:04
 * <p>
 * Description: Handles Login via Twitter, Facebook, Google and Github
 */
public class AuthLoginHandler {

    private static AuthLoginHandler ourInstance = new AuthLoginHandler();

    public static AuthLoginHandler getInstance() {
        return ourInstance;
    }

    private AuthLoginHandler() {}

    /**handles authorization with twitter by taking in a Twitter Session*/
    public static void handleTwitterSession(TwitterSession session, FirebaseAuth firebaseAuth, final Context context) {
        final String TAG = LoginActivity.LOGINACTIVITY;
        Log.d(TAG, "HandleTwitterSession:"+ session);

        /*get auth token and secret, get credentials*/
        AuthCredential authCredential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret
        );

        /*sign in user with Firebase credential*/
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            MainActivity.start(context);
                        }
                    }
                });
    }


    public static void AuthGoogleWithFirebase(GoogleSignInAccount account, FirebaseAuth auth, final Context context){
        final String TAG = LoginActivity.LOGINACTIVITY;
        Log.d(TAG, "FirebaseAuthWithGoogle"+account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "SignInWithCredentialComplete"+ task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                    Toast.makeText(context, "Authentication with Google failed.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.start(context);
                }
            }
        });

    }

    /**handles FacebookLogin token
     *
     * @param token Token received when their is successful login
     * @param auth Authenticator with Firebase
     * @param context The context this method will be called
     * @return isSucess, returns whether the process was successful*/
    public static boolean handleFacebookLoginToken(AccessToken token, FirebaseAuth auth,
                                                   final Context context){
        final boolean[] isSucess = {false};
        final String TAG = LoginActivity.LOGINACTIVITY;
        Log.d(TAG, "FirebaseWithFacebook:"+ token);

        // get the access token to get the credential and pass this to Firebase AUthCredential
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener((Activity) context,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "SignInWithCredentialFacebook: " + task.isSuccessful());
                /*if not successful, inform user on failure*/
                if(!task.isSuccessful()){
                    Log.w(TAG, "signInWithCredential:FacebookFailed: ", task.getException());
                    Toast.makeText(context, "Authentication with Facebook failed.",
                            Toast.LENGTH_SHORT).show();
                    isSucess[0] = false;
                }else{
                    isSucess[0] = true;
                }
            }
        });
        return isSucess[0];
    }
/*END*/
}
