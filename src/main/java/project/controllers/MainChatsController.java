package project.controllers;

import project.database.DBGetter;
import project.database.UpdateDB;
import project.models.Group;
import project.models.Personal;
import project.models.User;

import java.util.ArrayList;

public class MainChatsController  extends Controller{

    public ArrayList<Group> handleShowGroups(int id) {
        ArrayList<Group> groups =  DBGetter.findGroupsWithMemberID(id);
        if(groups.size() == 0){
            System.out.println("you are not added in any group");
        }
        return groups;
    }

    public String handleCreateGroup(String groupID, String groupName, int adminID) {
        if(DBGetter.findGroupByGroupID(groupID) != null) {
            return "this ID is already taken";
        }

        UpdateDB.createGroup(groupID, groupName, adminID);
        return "added successfully";
    }

    public String handleDeleteGroup(Group group, int userID) {
        if(group.getGroupAdminNumberID() != userID){
            return "you are not the admin";
        }

        UpdateDB.deleteGroup(group.getGroupNumberID());
        return "deleted successfully";
    }

    public boolean handleEnteringGroup(Group group, int id) {


        return DBGetter.checkMembership(id, group.getGroupNumberID());
    }

    public void handleCreatePrivateChat(String receiverID, int senderID) {
        User receiver = DBGetter.findUserByUserID(receiverID);
        if(receiver == null){
            System.out.println("the member id doesn't belong to any user");
            return;
        }
        if(DBGetter.checkPrivateChat(receiver.getId(), senderID)){
            System.out.println("you already have a chat with this user");
            return;
        }

        UpdateDB.createPrivateChat(receiver.getId(), senderID);
    }

    public ArrayList<Personal> handleShowPrivateChats(int numberID) {
        ArrayList<Personal> chats = DBGetter.findChatsWithMemberID(numberID);
        if(chats.size() == 0){
            System.out.println("you have no chats");
        }
        return chats;
    }

    public String handleDeletePrivateChat(int id1, int id2) {
        if(id1 == id2){
            return "you can't have a chat with yourself";
        }
        if(!DBGetter.checkPrivateChat(id1, id2)){
            return "you don't have a chat with this user";
        }

        UpdateDB.deletePrivateChat(id1, id2);
        return "deleted successfully";
    }
}
