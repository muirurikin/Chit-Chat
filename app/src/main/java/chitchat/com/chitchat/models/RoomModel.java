package chitchat.com.chitchat.models;

/**
 * Plain Old Java Object class for the Forum posts in the main activity
 * Model will have ImageView, Forum name and Badge counter displaying the number of new unread posts
 * */

public class RoomModel {
    private String forumName,unreadPosts, forumImageUrl;

    /**Empty constructor*/
    public RoomModel(){}

    /**Constructor that takes in the parameters to pass to the Adapter*/
    public RoomModel(String forumName, String unreadPosts, String forumImageUrl) {
        this.forumName = forumName;
        this.unreadPosts = unreadPosts;
        this.forumImageUrl = forumImageUrl;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getUnreadPosts() {
        return unreadPosts;
    }

    public void setUnreadPosts(String unreadPosts) {
        this.unreadPosts = unreadPosts;
    }

    public String getForumImageUrl() {
        return forumImageUrl;
    }

    public void setForumImageUrl(String forumImageUrl) {
        this.forumImageUrl = forumImageUrl;
    }
}