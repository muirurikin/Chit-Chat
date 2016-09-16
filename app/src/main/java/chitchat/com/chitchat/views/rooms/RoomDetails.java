package chitchat.com.chitchat.views.rooms;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import chitchat.com.chitchat.R;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views.rooms
 * Created by lusinabrian on 16/09/16 at 11:26
 * <p>
 * Description: RoomDetail activity that will be launched when a card item is clicked
 */

public class RoomDetails extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_details_act);
    }
}
