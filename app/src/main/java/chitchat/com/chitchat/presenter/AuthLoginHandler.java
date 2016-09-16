package chitchat.com.chitchat.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.views.LoginActivity;
import chitchat.com.chitchat.views.main.MainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter
 * Created by lusinabrian on 11/09/16 at 16:04
 * <p>
 * Description: Handles Login via Twitter, Facebook, Google and Github
 */
public class AuthLoginHandler{

    private static AuthLoginHandler ourInstance = new AuthLoginHandler();

    public static AuthLoginHandler getInstance() {
        return ourInstance;
    }
    private AuthLoginHandler() {}
    static SweetAlertDialog pDialog;

    /**handles authorization with twitter by taking in a Twitter Session*/
    public static boolean handleTwitterSession(TwitterSession session, FirebaseAuth firebaseAuth, final Context context) {
        final String TAG = LoginActivity.LOGINACTIVITY;
        final boolean[] isSucess = {false};
        Log.d(TAG, "HandleTwitterSession:"+ session);

        displayProgressDialog(context);

        /*get auth token and secret, get credentials*/
        AuthCredential authCredential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret
        );

        /*sign in user with Firebase credential*/
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential, Twitter", task.getException());
                            //dismiss the progress dialog if unsuccessful, display error
                            dismissProgressDialogWithSuccess(context, task.isSuccessful());
                            isSucess[0] = !task.isSuccessful();
                        }
                        else{
                            isSucess[0] = task.isSuccessful();
                        }
                    }
                });
        return isSucess[0];
    }

    public static void AuthGoogleWithFirebase(GoogleSignInAccount account, FirebaseAuth auth,
                                              final Context context){
        final String TAG = LoginActivity.LOGINACTIVITY;
        Log.d(TAG, "FirebaseAuthWithGoogle"+account.getId());
        displayProgressDialog(context);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        auth.signInWithCredential(credential).addOnCompleteListener((Activity) context,
                new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Log.d(TAG, "SignInWithCredentialComplete"+ task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            //dismiss the progress dialog if unsuccessful, display error
                            dismissProgressDialogWithSuccess(context, task.isSuccessful());
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
        displayProgressDialog(context);

        // get the access token to get the credential and pass this to Firebase AUthCredential
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener((Activity) context,
                new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "SignInWithCredentialFacebook: " + task.isSuccessful());
                        /*if not successful, inform user on failure*/
                        if(!task.isSuccessful()){
                            Log.w(TAG, "signInWithCredential:FacebookFailed: ", task.getException());
                            isSucess[0] = task.isSuccessful();

                            //dismiss the progress dialog if unsuccessful, display error
                            dismissProgressDialogWithSuccess(context, isSucess[0]);
                        }else{
                            isSucess[0] = task.isSuccessful();
                        }
                    }
                });
        return isSucess[0];
    }

    /**Display the progress dialog while loading*/
    private static void displayProgressDialog(Context context) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(context, R.color.blue));
        pDialog.setTitleText("Loading...please wait");
        pDialog.show();
    }

    /**Dismiss the progress dialog displaying either error message or sucess message*/
    private static void dismissProgressDialogWithSuccess(Context context, boolean success){
        if(pDialog.isShowing() && success){
            pDialog.setTitleText("Success!");
            pDialog.dismissWithAnimation();
        }else{
            pDialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("Authentication Failed. Please try again");
            pDialog.dismissWithAnimation();
        }
    }

/*END*/
}
