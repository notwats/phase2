package project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public static ArrayList<User> allUsers = new ArrayList<>(); // not needed that much

    String profileImage;

    int numberID;
    String username;
    String userID;
    private String password;
    String securityAnswer;
    Integer securityQuestion;
    Boolean isNormal; // actually boolean

    ArrayList<Integer> followersID = new ArrayList<>();
    ArrayList<Integer> followingsID = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<Integer> chatsID = new ArrayList<>();
    ArrayList<Integer> groupsID = new ArrayList<>();
    ArrayList<Integer> suggestionsID; // ???
    ArrayList<Integer> blockedUsersID;

    //business acc
    public HashMap<LocalDate, Integer> profileViews = new HashMap<>();


    public ArrayList<Integer> getSuggestionsID() {
        return suggestionsID;
    }

    public void setSuggestionsID(ArrayList<Integer> suggestionsID) {
        this.suggestionsID = suggestionsID;
    }

    LocalDateTime createDate;

    String Bio;

//    private static int mainID = 0;
public User(){}

    public User(Integer userNumberId, String userID, String username, String password, String securityAnswer, Integer securityQuestion, int type) {

        this.numberID = userNumberId;
        // this.id = mainID++;
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.securityQuestion = securityQuestion;
        if (type == 1) {
            isNormal = true;
        } else if (type == 0) {
            isNormal = false;
        } else {
            System.out.println("error in type user db");
        }
        this.createDate = LocalDateTime.now();
        allUsers.add(this);
    }

    public User(String userID, String username, String password, String securityAnswer, Integer securityQuestion, Boolean type) {

        //  this.numberID=userNumberId;
        // this.id = mainID++;
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.securityQuestion = securityQuestion;
        isNormal = type;
        this.createDate = LocalDateTime.now();
        allUsers.add(this);
    }
//
//
//    public static User getUserByUsername(String username) {
//        for (User user : User.allUsers) {
//            if (user.username.equals(username)) {
//                return user;
//            }
//
//        }
//        return null;
//    }
//
//    public static User findUserByUserNumberID(int id) {
//        for (User user :
//                User.allUsers) {
//            if (user.id == id) {
//                return user;
//            }
//        }
//        return null;
//    }

    public void follow(int followingID) {
        //add id to database;
    }

    public ArrayList<Post> getPosts() {
        Collections.sort(posts);
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Integer> getBlockedUsersID() {
        return blockedUsersID;
    }

    public void setBlockedUsersID(ArrayList<Integer> blockedUsersID) {
        this.blockedUsersID = blockedUsersID;
    }

    public void liking(int postID) {
        //add id to database;
    }

    public void commenting(int postID, String comment) {
        // new object comment
        //add id to database;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }


    public void posting() {
        // new post object
        // add to database
    }

    public void messaging(int groupID, String massage) {
        // new massage object
        // add to data base
    }

    public void chatting(int chatID, String massage) {
        // new massage object
        // add to data base
    }

    public void deletingac() {
        //delet databases related to userID
    }


    public int getId() {
        return numberID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getFollowersID() {
        return followersID;
    }

    public ArrayList<Integer> getFollowingsID() {
        return followingsID;
    }

    public ArrayList<Integer> getChatsID() {
        return chatsID;
    }

    public ArrayList<Integer> getGroupsID() {
        return groupsID;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getBio() {
        return Bio;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public Integer getSecurityQuestion() {
        return securityQuestion;
    }


    public void setId(int id) {
        this.numberID = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFollowersID(ArrayList<Integer> followersID) {
        this.followersID = followersID;
    }

    public void setFollowingsID(ArrayList<Integer> followingsID) {
        this.followingsID = followingsID;
    }

    public void setChatsID(ArrayList<Integer> chatsID) {
        this.chatsID = chatsID;
    }

    public void setGroupsID(ArrayList<Integer> groupsID) {
        this.groupsID = groupsID;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setSecurityQuestion(Integer securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}