package chitchat.com.chitchat.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import chitchat.com.chitchat.Contract;
import chitchat.com.chitchat.R;
import chitchat.com.chitchat.presenter.adapters.ForumAdapter;
import chitchat.com.chitchat.models.RoomModel;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private static final String MAINACTIVITY_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<RoomModel, ForumAdapter.ViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        initFirebaseDatabase();
    }

    /**Initialize the UI controls*/
    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.forums_recycler_view_id);
        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout)findViewById(R.id.forums_waveswiperefresh_layout_id);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
    }

    /**Initialize the Firebase database*/
    private void initFirebaseDatabase() {
        // initialize the Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String forumNode = dataSnapshot.getValue(String.class);
                Log.d(MAINACTIVITY_TAG, "Value: "+ forumNode);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(MAINACTIVITY_TAG, "Failed to read value " + databaseError.toException());
            }
        });
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoomModel, ForumAdapter.ViewHolder>(
                RoomModel.class,
                R.layout.forum_item,
                ForumAdapter.ViewHolder.class,
                mDatabase.child(Contract.FORUMNODE)
        ) {
            @Override
            protected void populateViewHolder(ForumAdapter.ViewHolder viewHolder, RoomModel model, int position) {
                viewHolder.forumName.setText(model.getForumName());
            }
        };

        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int forumsCount = firebaseRecyclerAdapter.getItemCount();
                int lastVisibleForum = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll to the bottom of the list to show the newly added message.
                if (lastVisibleForum == -1 ||
                        (positionStart >= (forumsCount - 1) && lastVisibleForum == (positionStart - 1))) {
                    mRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        // Set the layout manager and adapter
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

    /**
     * Task to refresh data from Firebase database
     * */
    private class FetchTask extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            waveSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
