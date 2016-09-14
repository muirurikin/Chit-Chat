package chitchat.com.chitchat.views.main;

import java.util.List;

import chitchat.com.chitchat.models.RoomModel;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views.main
 * Created by lusinabrian on 14/09/16 at 09:24
 * <p>
 * Description: View interface for MainActivity
 */

public interface MainView {
    /**Show loading Progress for Forum items*/
    void showProgress();

    /**Hide the progress once loading is done*/
    void hideProgress();

    void showMessage(String msg);

    void setItems(List<RoomModel> roomModelList);

    void openForum();
}
