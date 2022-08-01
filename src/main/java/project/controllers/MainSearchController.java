package project.controllers;

import project.database.DBGetter;
import project.models.Group;
import project.models.User;

public class MainSearchController extends Controller {

    public static User searchAccounts(String userID) {
        return DBGetter.findUserByUserID(userID);
    }

    public static Group searchGroups(String groupID) {
        return DBGetter.findGroupByGroupID(groupID);
    }
//    public static ?? search(String tag){
//        //  ifexist
//        //           showallof them
//        //   ...
//
//    }

}
