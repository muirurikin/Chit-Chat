package chitchat.com.chitchat.views.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import chitchat.com.chitchat.utils.ActivityUtils;
import chitchat.com.chitchat.views.rooms.RoomsFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity{
    private static final String MAINACTIVITY_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    private DrawerLayout mDrawerLayout;
    private MainPresenter mainPresenter;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                //TODO: open settings
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    /**START THIS PARTICULAR ACTIVITY*/
    public static void start(Context c) {
        c.startActivity(new Intent(c, MainActivity.class));
    }
}
