package chitchat.com.chitchat.presenter.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import chitchat.com.chitchat.R;
import chitchat.com.chitchat.models.RoomModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.presenter.adapters
 * Created by lusinabrian on 07/09/16 at 17:34
 * <p>
 * Description: Adapter class that will handle the Recycling of views in the Main Activity.
 * This will populate the RecyclerView in the Main Activity
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    private Context context;
    private List<RoomModel> roomModelList;
    private int ItemLayout;

    /**Constuctor to initialize this adapter
     * @param context Context in which this adapter is called
     * @param roomModelList List in which data will be extracted and transfered to items
     * @param itemLayout Layout to inflate data with**/
    public RoomAdapter(Context context, List<RoomModel> roomModelList, int itemLayout) {
        this.context = context;
        this.roomModelList = roomModelList;
        ItemLayout = itemLayout;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {#onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(ItemLayout, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * Override {onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RoomModel roomModel =  roomModelList.get(position);
        holder.itemView.setTag(roomModel);
        holder.bind(roomModel);
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private CircleImageView forumImage;
        private TextView forumName;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            forumName = (TextView)itemView.findViewById(R.id.forum_item_name);
            forumImage = (CircleImageView)itemView.findViewById(R.id.forum_item_img);
        }

        /*binds the item views to the model class*/
        public void bind(RoomModel roomModel){
            forumName.setText(roomModel.getRoom_name());
            if(roomModel.getImg_url() == null){
                forumImage.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_account_circle_black_36dp));
            }else{
                Glide.with(context)
                        .load(roomModel.getImg_url())
                        .into(forumImage);
            }

        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return roomModelList.size();
    }

    public void add(RoomModel itemModel, int position){
        roomModelList.add(position,itemModel);
        notifyDataSetChanged();
    }

}
