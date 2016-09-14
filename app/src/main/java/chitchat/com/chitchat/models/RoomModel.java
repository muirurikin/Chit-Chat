package chitchat.com.chitchat.models;

/**
 * Plain Old Java Object class for the Forum posts in the main activity
 * Model will have ImageView, Forum name and Badge counter displaying the number of new unread posts
 * */

public class RoomModel {
    private String rooms,unreadPosts, img_url;

    /**Empty constructor*/
    public RoomModel(){}

    /**Constructor that takes in the parameters to pass to the Adapter*/
    public RoomModel(String rooms, String unreadPosts, String img_url) {
        this.rooms = rooms;
        this.unreadPosts = unreadPosts;
        this.img_url = img_url;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getUnreadPosts() {
        return unreadPosts;
    }

    public void setUnreadPosts(String unreadPosts) {
        this.unreadPosts = unreadPosts;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}