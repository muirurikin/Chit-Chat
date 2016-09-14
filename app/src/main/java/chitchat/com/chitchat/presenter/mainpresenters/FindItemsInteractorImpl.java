package chitchat.com.chitchat.presenter.mainpresenters;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.models.RoomModel;
import chitchat.com.chitchat.presenter.Contract;
import chitchat.com.chitchat.presenter.adapters.RoomAdapter;
import chitchat.com.chitchat.views.LoginActivity;
import chitchat.com.chitchat.views.main.MainActivity;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.mainpresenters
 * Created by lusinabrian on 14/09/16 at 10:12
 * Description: finds the items and passes them to the adapter for viewing in the MainActivity
 */

public class FindItemsInteractorImpl implements FindItemsInteractor {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void findItems(final OnFinishedListener listener) {
        listener.onFinished(createModelList());
    }

    private List<RoomModel> createModelList(){
        // initialize the Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RoomModel, RoomAdapter.ViewHolder>(
                RoomModel.class,
                R.layout.room_item,
                RoomAdapter.ViewHolder.class,
                mDatabase.child(Contract.ROOMSNODE).child(Contract.ROOMNAME)
        ) {
            @Override
            protected void populateViewHolder(RoomAdapter.ViewHolder viewHolder, RoomModel model,
                                              int position) {
                Log.d(LoginActivity.LOGINACTIVITY,
                        "Image URL: " + model.getImg_url() + "Name: "+ model.getRoom_name()+
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
        return null;

    }
/*END*/
}
