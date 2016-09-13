package chitchat.com.chitchat.presenter;

import chitchat.com.chitchat.BuildConfig;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat
 * Created by lusinabrian on 08/09/16 at 22:11
 * Description: Contract class containing constant definitions for the Firebase database, consumer keys and consumer secrets.
 */

public class Contract {
    public static final String ROOMSNODE = "rooms";
    public static final String ROOMNAME = "name";
    public static final String TWITTER_CONSUMER_KEY = BuildConfig.TWITTER_CONSUMER_KEY;
    public static final String TWITTER_SECRET = BuildConfig.TWITTER_SECRET;
    public static final int RC_SIGN_IN = 9001;
}
