package chitchat.com.chitchat.views.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import chitchat.com.chitchat.views.rooms.RoomsFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements MainView{
    private static final String MAINACTIVITY_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    private ListView roomList;
    private DrawerLayout mDrawerLayout;
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
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mainPresenter = new MainPresenterImpl(this, new FindItemsInteractorImpl());
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        // set up the navigation drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.mainactivity_drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimary);
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);
        if(navigationView != null){
            setUpDrawerContents(navigationView);
        }

        //create the fragment
        RoomsFragment roomsFragment = (RoomsFragment) getSupportFragmentManager().findFragmentById(R.id.mainactivity_contentFrame);
        if (roomsFragment ==null){
            roomsFragment = RoomsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), roomsFragment, R.id.mainactivity_contentFrame);
        }
        initViews();
        initFirebaseDatabase();
    }

    /**Sets up the contents of the DrawerLayout*/
    private void setUpDrawerContents(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.sign_out_menu_item:
                                //sign out the user with firebase
                                break;
                            default:
                                break;
                        }
                        //close the navigation drawer when an item is clicked
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
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
                        "Image URL: " + model.getImg_url() + "Name: "+ model.getRoom_name()+
                                "Unreads: ");
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
