package chitchat.com.chitchat.models;

/**
 * Plain Old Java Object class for the Forum posts in the main activity
 * Model will have ImageView, Forum name and Badge counter displaying the number of new unread posts
 * */

public class RoomModel {
    private String room_name, img_url;

    /**Empty constructor*/
    public RoomModel(){}

    /**Constructor that takes in the parameters to pass to the Adapter*/
    public RoomModel(String room_name,String img_url) {
        this.room_name = room_name;
        this.img_url = img_url;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}