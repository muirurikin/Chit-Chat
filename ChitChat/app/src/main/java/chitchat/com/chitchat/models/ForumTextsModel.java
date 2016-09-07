package chitchat.com.chitchat.models;

/**
 * Project: ChitChat
 * Package: chitchat.com.chitchat.models
 * Created by lusinabrian on 07/09/16 at 17:28
 * <p>
 * Description: Contains the Model class for individual Forum views.
 * The forum view will have a photo for the user, name of the messenger, time of the message, message
 */

public class ForumTextsModel {
    private String photoUrl, messengerName, messegeTime, message;

    public ForumTextsModel(){}

    public ForumTextsModel(String photoUrl, String messengerName, String messegeTime, String message) {
        this.photoUrl = photoUrl;
        this.messengerName = messengerName;
        this.messegeTime = messegeTime;
        this.message = message;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }

    public String getMessegeTime() {
        return messegeTime;
    }

    public void setMessegeTime(String messegeTime) {
        this.messegeTime = messegeTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
