package chitchat.com.chitchat.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import chitchat.com.chitchat.MainActivity;
import chitchat.com.chitchat.R;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 07/09/16 at 17:02
 * <p>
 * Description: App introduction, will only be fired once by the application
 */

public class AppIntroduction extends AppIntro2{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //add fragments for the introduction (3 slides)
        //TODO: change titles and descriptions
        addSlide(AppIntroFragment.newInstance("","",R.mipmap.ic_launcher, ContextCompat.getColor(this,R.color.dark_orange)));
        addSlide(AppIntroFragment.newInstance("","",R.mipmap.ic_launcher, ContextCompat.getColor(this,R.color.orange_1)));
        addSlide(AppIntroFragment.newInstance("","",R.mipmap.ic_launcher, ContextCompat.getColor(this,R.color.light_orange2)));

        /*animation*/
        setDepthAnimation();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        //do nothing
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //open main activity
        openMain();
        //kill this to save memory
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        //open Main activity
        openMain();
        finish();
    }


    /**open main activity*/
    public void openMain(){
        Intent openMain = new Intent(AppIntroduction.this, MainActivity.class);
        startActivity(openMain);
    }
}
