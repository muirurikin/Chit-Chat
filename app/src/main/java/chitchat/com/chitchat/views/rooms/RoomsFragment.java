package chitchat.com.chitchat.views.rooms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import chitchat.com.chitchat.R;
import chitchat.com.chitchat.models.RoomModel;
import chitchat.com.chitchat.presenter.Contract;
import chitchat.com.chitchat.presenter.adapters.RoomAdapter;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views
 * Created by lusinabrian on 15/09/16 at 12:37
 * Display a grid of {@link RoomModel}s.
 */

public class RoomsFragment extends Fragment implements RoomsContract.View {
    private static final String ROOMSFRAGMENTTAG = RoomsFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    private FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder> firebaseRecyclerAdapter;
    private RoomAdapter roomAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private SweetAlertDialog progressDialog;
    private DatabaseReference mDatabase;
    private List<RoomModel> roomModelList;
    private RoomsContract.Presenter roomsPresenter;

    /*required public empty constructor*/
    public RoomsFragment(){}

    public static RoomsFragment newInstance(){
        return new RoomsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomModelList = new ArrayList<>();
        roomAdapter = new RoomAdapter(getActivity(), roomModelList, R.layout.room_item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rooms_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rooms_recycler_view_id);
        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout)rootView.findViewById(R.id.rooms_waveswiperefresh_layout_id);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        initFirebaseDatabase();
        return rootView;
    }

    // TODO: OVERRIDE?
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //roomsPresenter.result(requestCode,resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**Initialize the Firebase database*/
    private void initFirebaseDatabase() {
        // initialize the Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Contract.ROOMSNODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RoomModel roomModel = dataSnapshot.getValue(RoomModel.class);

                roomModel = new RoomModel(roomModel.getRoom_name(), roomModel.getImg_url());
                roomModelList = new ArrayList<>();
                roomModelList.add(roomModel);
                roomAdapter = new RoomAdapter(getActivity(),roomModelList,R.layout.room_item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder>(
                RoomModel.class,
                R.layout.room_item,
                RoomAdapter.ViewHolder.class,
                mDatabase.child(Contract.ROOMSNODE)) {
            @Override
            protected void populateViewHolder(RoomAdapter.ViewHolder viewHolder, RoomModel model,
                                              int position) {
                Log.d(ROOMSFRAGMENTTAG, "Image URL: " + model.getImg_url() + " Name: "+ model.getRoom_name());
                viewHolder.roomName.setText(model.getRoom_name());
                if (model.getImg_url() == null || model.getImg_url().isEmpty()) {
                    viewHolder.roomImage.setImageDrawable(
                            ContextCompat.getDrawable(getActivity(),
                                    R.mipmap.ic_launcher));
                } else {
                    Glide.with(getActivity())
                            .load(model.getImg_url())
                            .into(viewHolder.roomImage);
                }
            }
        };

/*        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
        });*/

        // Set the layout manager and adapter
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void showRooms(List<RoomModel> roomList) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void cancelLoadingIndicator(boolean notActive){
        if(progressDialog.isShowing()){
            progressDialog.dismissWithAnimation();
        }
    }

    @Override
    public void showLoadingRoomsError() {

    }

    @Override
    public void setPresenter(@NonNull RoomsContract.Presenter presenter) {
        roomsPresenter = checkNotNull(presenter);
    }

}
