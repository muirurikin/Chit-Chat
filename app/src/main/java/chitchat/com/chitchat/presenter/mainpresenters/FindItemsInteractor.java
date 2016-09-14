package chitchat.com.chitchat.presenter.mainpresenters;

import java.util.List;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.mainpresenters
 * Created by lusinabrian on 14/09/16 at 09:36
 * <p>
 * Description: Interaction for finding items in The RecyclerView
 */

public interface FindItemsInteractor {

    interface OnFinishedListener{
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}
