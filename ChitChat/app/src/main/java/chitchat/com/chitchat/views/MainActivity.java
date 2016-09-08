package chitchat.com.chitchat.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.adapters.ForumAdapter;
import chitchat.com.chitchat.models.ForumModel;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<ForumModel, ForumAdapter.ViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initFirebaseDatabase();
    }

    /**Initialize the Firebase database*/
    private void initFirebaseDatabase() {
        // initialize the Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ForumModel, ForumAdapter.ViewHolder>(
                ForumModel.class,
                R.layout.forum_item,
                ForumAdapter.ViewHolder.class,
                mDatabase.child()
        ) {
            @Override
            protected void populateViewHolder(ForumAdapter.ViewHolder viewHolder, ForumModel model, int position) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
