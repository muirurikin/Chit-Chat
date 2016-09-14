package chitchat.com.chitchat.presenter.mainpresenters;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.mainpresenters
 * Created by lusinabrian on 14/09/16 at 09:28
 * <p>
 * Description: presenter for MainActivity, middlemane between the model and the MainActivity
 */

public interface MainPresenter {
    /**when the activity is resumed*/
    void onResume();

    /**called when an item in RecyclerView is clicked*/
    void onItemClicked(int position);

    /**called when the activity is killed*/
    void onDestroy();
}
