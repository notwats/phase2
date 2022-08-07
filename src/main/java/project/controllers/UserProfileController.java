package project.controllers;

import project.database.UserDB;
import project.models.User;

import static project.SceneController.loggedInUser;

public class UserProfileController extends Controller {



 //   public UserProfileController(User user) {
 //       this.currentProfile = user;
  //  }

    public static void block(User currentUser) {
        // logged in user do with current user
    }

    public static void mute(User currenUser) {

    }

    public static void followHandle(User currentProfile) {
        if (isFollower(currentProfile)) {
            UserDB.unFollow(loggedInUser.getNumberID(), currentProfile.getNumberID());
            loggedInUser.getFollowingsID().remove((Integer) currentProfile.getNumberID());
            currentProfile.getFollowersID().remove((Integer) loggedInUser.getNumberID());
            System.out.println("unfollowed successfully");
        } else {
            UserDB.follow(loggedInUser.getNumberID(), currentProfile.getNumberID());
            loggedInUser.getFollowingsID().add(currentProfile.getNumberID());
            currentProfile.getFollowersID().add(loggedInUser.getNumberID());
            System.out.println("followed successfully");

        }

    }

    // public static ArrayList<Post> showAllPost(User )


    private static boolean isFollower(User currentProfile) {
        return currentProfile.getFollowersID().contains(loggedInUser.getId());
    }


}
