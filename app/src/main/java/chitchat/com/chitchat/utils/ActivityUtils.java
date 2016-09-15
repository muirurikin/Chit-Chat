package chitchat.com.chitchat.utils;

import android.support.v4.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.utils
 * Created by lusinabrian on 15/09/16 at 12:43
 */

public class ActivityUtils {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
