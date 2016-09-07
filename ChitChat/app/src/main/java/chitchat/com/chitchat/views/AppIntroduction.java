package chitchat.com.chitchat.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 07/09/16 at 17:02
 * <p>
 * Description:
 */

public class AppIntroduction extends AppIntro2{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        addSlide(AppIntroFragment.newInstance("","",, ContextCompat.getColor()));
    }
}
