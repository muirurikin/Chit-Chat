package chitchat.com.chitchat.views.rooms;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.models.RoomModel;
import chitchat.com.chitchat.presenter.Contract;
import chitchat.com.chitchat.presenter.adapters.RoomAdapter;
import chitchat.com.chitchat.views.main.MainView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 15/09/16 at 12:37
 * <p>
 * Description: Fragment holds the views of the rooms the user can see. Has the recyclerview
 */

public class RoomsFragment extends Fragment implements MainView {
    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    private FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private SweetAlertDialog progressDialog;

    public RoomsFragment(){}

    public static RoomsFragment newInstance(){
        return new RoomsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rooms_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rooms_recycler_view_id);
        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout)rootView.findViewById(R.id.rooms_waveswiperefresh_layout_id);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        initFirebaseDatabase();

        return rootView;
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
    public void setItems(List<RoomModel> roomModelList) {
        mRecyclerView.setAdapter(new RoomAdapter(this, roomModelList,R.layout.room_item));
    }

    @Override
    public void openForum() {

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
    public void showMessage(String msg) {

    }

}
