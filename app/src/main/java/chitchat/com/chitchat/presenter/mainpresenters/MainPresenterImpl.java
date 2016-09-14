package chitchat.com.chitchat.presenter.mainpresenters;

import java.util.List;

import chitchat.com.chitchat.models.RoomModel;
import chitchat.com.chitchat.views.main.MainView;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.mainpresenters
 * Created by lusinabrian on 14/09/16 at 09:34
 * Description: Implementation of the MainPresenter Interface
 */

public class MainPresenterImpl implements MainPresenter,FindItemsInteractor.OnFinishedListener{
    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;

    /**Constructor*/
    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor){
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
    }

    /**
     * when the activity is resumed
     */
    @Override
    public void onResume() {
        if(mainView != null){

        }
        mainView.showProgress();
        findItemsInteractor.findItems(this);
    }

    /**
     * called when an item in RecyclerView is clicked
     *Should start the next activity for the particular item clicked.
     * @param position of the item being clicked
     */
    @Override
    public void onItemClicked(int position) {
        if(mainView != null){
            //TODO: pick details from the mainActivity, transfer to next activity
            mainView.showMessage(String.format("Position %d clicked", position + 1));
        }
    }

    /**
     * called when the activity is killed
     */
    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<RoomModel> roomModelList) {
        if(mainView != null){
            mainView.setItems(roomModelList);
            mainView.hideProgress();
        }
    }

    public MainView getMainView(){
        return mainView;
    }
}
