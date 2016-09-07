package chitchat.com.chitchat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import chitchat.com.chitchat.models.ForumModel;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.adapters
 * Created by lusinabrian on 07/09/16 at 17:34
 * <p>
 * Description: Adapter class that will handle the Recycling of views in the Main Activity.
 * This will populate the RecyclerView in the Main Activity
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder>{
    private Context context;
    private List<ForumModel> forumModelList;
    private int ItemLayout;

    /**Constuctor to initialize this adapter
     * @param context Context in which this adapter is called
     * @param forumModelList List in which data will be extracted and transfered to items
     * @param itemLayout Layout to inflate data with**/
    public ForumAdapter(Context context, List<ForumModel> forumModelList, int itemLayout) {
        this.context = context;
        this.forumModelList = forumModelList;
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
        ForumModel forumModel =  forumModelList.get(position);
        holder.itemView.setTag(forumModel);
        holder.bind(forumModel);
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView forumImage;
        public TextView forumName;
        public TextView unreadMessages;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /*binds the item views to the model class*/
        public void bind(ForumModel forumModel){

        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return forumModelList.size();
    }

    public void add(ForumModel itemModel, int position){
        forumModelList.add(position,itemModel);
        notifyDataSetChanged();
    }

}
