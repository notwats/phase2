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


    public String handleCreatePrivateChat(String receiverID, int senderID) {
        User receiver = DBGetter.findUserByUserID(receiverID);
        if(receiver == null){
            return "the member id doesn't belong to any user";
        }

        if(DBGetter.checkPrivateChat(receiver.getId(), senderID)){
            return"you already have a chat with this user";

        }

        UpdateDB.createPrivateChat(receiver.getId(), senderID);
        return "successfully created the chat";
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
