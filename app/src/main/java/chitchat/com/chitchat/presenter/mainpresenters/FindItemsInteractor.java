package chitchat.com.chitchat.presenter.mainpresenters;

import java.util.List;

import chitchat.com.chitchat.models.RoomModel;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.mainpresenters
 * Created by lusinabrian on 14/09/16 at 09:36
 * <p>
 * Description: Interaction for finding items in The RecyclerView
 */

public interface FindItemsInteractor {

    /**called when loading is done, sets the items to the RecyclerVIew*/
    interface OnFinishedListener{
        void onFinished(List<RoomModel> items);
    }

    void findItems(OnFinishedListener listener);
}
