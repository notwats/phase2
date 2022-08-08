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

    int likeNumber;

    String context; // main for phase 1

    LocalDate creationDate;
    ArrayList<Integer> commentsid = new ArrayList<>();
    ArrayList<Integer> likedUsersid = new ArrayList<>();

    public ArrayList<LocalDate> likesDate = new ArrayList<>();
    public ArrayList<LocalDate> viewsDate = new ArrayList<>();


    ArrayList<Tag> tags;


    @Override
    public int compareTo(Post post) {
        int compareId = post.getPostID();
        return -this.postID + compareId;
    }
}

