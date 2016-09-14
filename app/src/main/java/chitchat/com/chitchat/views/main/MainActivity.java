package chitchat.com.chitchat.views.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import chitchat.com.chitchat.presenter.Contract;
import chitchat.com.chitchat.R;
import chitchat.com.chitchat.presenter.adapters.RoomAdapter;
import chitchat.com.chitchat.models.RoomModel;
import chitchat.com.chitchat.presenter.mainpresenters.FindItemsInteractorImpl;
import chitchat.com.chitchat.presenter.mainpresenters.MainPresenter;
import chitchat.com.chitchat.presenter.mainpresenters.MainPresenterImpl;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements MainView{
    private static final String MAINACTIVITY_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    private ListView roomList;
    private FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    private MainPresenter mainPresenter;
    private SweetAlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainPresenter = new MainPresenterImpl(this, new FindItemsInteractorImpl());
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        initViews();
        initFirebaseDatabase();
    }
    /**Initialize the UI controls*/
    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view_id);
        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout)findViewById(R.id.rooms_waveswiperefresh_layout_id);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
    }


    @Override
    public void setItems(List<RoomModel> roomModelList) {
        mRecyclerView.setAdapter(new RoomAdapter(this, roomModelList,R.layout.room_item));
    }

    @Override
    public void showProgress() {
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if(progressDialog.isShowing()){
            progressDialog.dismissWithAnimation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
    }


    /**Initialize the Firebase database*/
    private void initFirebaseDatabase() {
        // initialize the Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.child(Contract.ROOMSNODE).getChildren();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder>(
                RoomModel.class,
                R.layout.room_item,
                RoomAdapter.ViewHolder.class,
                mDatabase.child(Contract.ROOMSNODE).child(Contract.ROOMNAME)
        ) {
            @Override
            protected void populateViewHolder(RoomAdapter.ViewHolder viewHolder, RoomModel model,
                                              int position) {
                Log.d(MAINACTIVITY_TAG,
                        "Image URL: " + model.getImg_url() + "Name: "+ model.getRooms()+
                                "Unreads: " + model.getUnreadPosts());
                viewHolder.bind(model);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void showMessage(String msg) {

    }


    @Override
    public void openForum() {

    }

    /**START THIS PARTICULAR ACTIVITY*/
    public static void start(Context c) {
        c.startActivity(new Intent(c, MainActivity.class));
    }
}
