package chitchat.com.chitchat.views.rooms;

import chitchat.com.chitchat.BasePresenter;
import chitchat.com.chitchat.BaseView;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.views.rooms
 * Created by lusinabrian on 15/09/16 at 13:00
 * <p>
 * Description: Specifies the contract between the View and the presenter
 */

public class RoomsContract {
    interface View extends BaseView<Presenter>{
        
    }

    interface Presenter extends BasePresenter{

    }
}
