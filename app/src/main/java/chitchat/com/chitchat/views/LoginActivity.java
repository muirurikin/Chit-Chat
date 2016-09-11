package chitchat.com.chitchat.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Contract.TWITTER_CONSUMER_KEY, Contract.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.loginactivity_layout);
    }
}
