package project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post implements Comparable<Post> {

    public String imageAddress;


    int postID;
    Integer senderid;

    Boolean isNormal = true;
    /*
    int imageID;
    int voiceID;
    int audioID;
     */
    int likeNumber;

    String context; // main for phase 1

    Date creationDate;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<User> likedUsers = new ArrayList<>();

    public HashMap<Date, Integer> likesPerday = new HashMap<>();
    public HashMap<Date, Integer> viewsPerday = new HashMap<>();
    ArrayList<Tag> tags;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(ArrayList<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void forward() {
    }

    public void edit() {
    }

    public void reaction() {
    }

    public void view() {
    }

    public void delete() {
    }

    public void report() {
    }

    public void comment() {
    }


    @Override
    public int compareTo(Post post) {
        int compareId = post.getPostID();
        return -this.postID + compareId;
    }
}

