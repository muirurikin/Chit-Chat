package chitchat.com.chitchat.models;

/**
 * Plain Old Java Object class for the Forum posts in the main activity
 * Model will have ImageView, Forum name and Badge counter displaying the number of new unread posts
 * */

public class ForumModel{
    private String forumName,unreadPosts,forumImage;

    /**Empty constructor*/
    public ForumModel(){}

    /**Constructor that takes in the parameters to pass to the Adapter*/
    public ForumModel(String forumName, String unreadPosts, String forumImage) {
        this.forumName = forumName;
        this.unreadPosts = unreadPosts;
        this.forumImage = forumImage;
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

    public String getForumImage() {
        return forumImage;
    }

    public void setForumImage(String forumImage) {
        this.forumImage = forumImage;
    }
}