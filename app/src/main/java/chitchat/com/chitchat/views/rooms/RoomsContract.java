package chitchat.com.chitchat.views.rooms;

import android.support.annotation.NonNull;

import java.util.List;

import chitchat.com.chitchat.BasePresenter;
import chitchat.com.chitchat.BaseView;
import chitchat.com.chitchat.models.RoomModel;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views.rooms
 * Created by lusinabrian on 15/09/16 at 13:00
 * <p>
 * Description: Specifies the contract between the View and the presenter
 */

public class RoomsContract {
    interface View extends BaseView<Presenter>{

        void showRooms(List<RoomModel> roomList);

        void setLoadingIndicator(boolean active);

        void cancelLoadingIndicator(boolean notActive);

        void showLoadingRoomsError();
    }

    interface Presenter extends BasePresenter{
        void result(int requestCode, int resultCode);

        void loadRooms(boolean forceUpdate);

        void addNewTask();

        void openRoomChats(@NonNull RoomModel requestedTask);

    }
}
