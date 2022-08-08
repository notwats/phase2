package project.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import project.SceneController;
import project.controllers.PrivateChatController;
import project.database.DBGetter;
import project.models.Group;
import project.models.GroupMessage;
import project.models.*;
import project.database.* ;

import project.Main;
import project.SceneController;
import project.views.welcome.WelcomeView;

import static project.SceneController.loggedInUser;

public class MainProfileController extends Controller {

    public static String showInfo() {
        String info = "userID: " + loggedInUser.getUserID() +
                "\n" + "username: " + loggedInUser.getUsername() //+
                //          "\n" + "followers num : " + loggedInUser.getFollowersID().size() +
                //           "\n" + " followings num : " + loggedInUser.getFollowingsID().size()
                ;
        // bio

        return info;
    }

    public static void deleteAcc() throws IOException {
        UserDB.deleteUser(loggedInUser);
        System.out.println("account deleted successfully");
        WelcomeView.run();
    }

    public static void makePrivate() {
    }

    public static void notif() {
    }

    public static void changeInfo(User loggedInUser) {
        UserDB.updateUser(loggedInUser);
        System.out.println("changed successfully");
    }

    public static void deletePost(Integer postID) {
        PostDB.deletePost(postID);
        System.out.println("post deleted successfully");
    }

    public static void changePost(Integer postID, String context) {

        Post wanted = PostDB.getPostByPostID(postID);
        wanted.setContext(context);
        PostDB.updatePost(wanted);
        System.out.println("post edited successfully");
    }


    // public void showAllPost() {

    //  }

    public static void handleNewPost(String context) {
        Post post = new Post();
        post.setSenderid(loggedInUser.getNumberID());
        post.setContext(context);
        LocalDate dateOfNow = LocalDate.now();
        post.setCreationDate(dateOfNow);
        post.setIsNormal(loggedInUser.getIsNormal());
        PostDB.addPost(post);
        System.out.println("Post created successfully");
    }
}
