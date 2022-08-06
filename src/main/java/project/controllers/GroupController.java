package project.controllers;

import project.database.DBGetter;
import project.database.UpdateDB;
import project.models.Group;
import project.models.GroupMessage;
import project.models.User;

import java.util.Date;

public class GroupController extends Controller{

    public void handelEditMessage(String editedText, int messageID) {
        GroupMessage message = DBGetter.findMessageByMessageID(messageID);
        message.editGroupMessage(editedText);
    }


    public String handleGroupNameChange(String groupName, int groupNumberID, String newName) {
        if(newName.length() >= 25){
            return "group name is too long";
        }

        if(groupName.equals(newName)){
            return "the new name is the same as before";
        }

        UpdateDB.changeGroupName(groupNumberID, newName);
        return ("group name changed to " + newName + " successfully" );
    }

    public String handleGroupIDChange(int groupNumberID, String groupID, String newGroupID) {
        if(newGroupID.length() >= 25){
            return "group id is too long";
        }

        if(newGroupID.equals(groupID)){
            return "the new id is the same as before";
        }

        if( DBGetter.findGroupByGroupID(newGroupID) != null){
            return "this groupID is taken";
        }


        UpdateDB.changeGroupID(groupNumberID, newGroupID);

        return "ID changed successfully";
    }

    public boolean handleSendMessage(String message, int senderID, int groupNumberID, Date dateOfNow, int i, int inReplyTo) {
        // ban check
        if(DBGetter.banCheck(groupNumberID, senderID)){
            System.out.println("you're banned from sending a message in this chat");
            return false;
        }


        UpdateDB.messageCreationInGroup(message, senderID, groupNumberID, dateOfNow, i, inReplyTo );
        return true;
    }

    public String handleAddMember(String memberID, Group group, int adminID) {
        if(adminID != group.getGroupAdminNumberID()){
            return "you are not the admin";
        }
        User newMember = DBGetter.findUserByUserID(memberID);
        if(newMember == null){
            return "the member isn't for anyone";
        }
        // check whether the admin is blocked by user or not ]
        if(DBGetter.BlockedByBLocker(adminID, newMember.getId() )){
            return "you are blocked by the user";
        }
        // check whether the member isn't already in the chat
        if(DBGetter.checkMembership(newMember.getId(), group.getGroupNumberID())){
            return "user is already in the group";
        }

        UpdateDB.addMemberToGroup(group.getGroupNumberID(), newMember.getId());
        return "successfully added";
    }

    public String handleRemoveMember(String memberID, Group group, int adminID) {
        if(adminID != group.getGroupAdminNumberID()){
            return "you are not the admin of this group";
        }
        User newMember = DBGetter.findUserByUserID(memberID);
        if(newMember == null){
            return "the member id doesn't belong to any user";
        }
        // check whether user is a member of the group
        if(!DBGetter.checkMembership(newMember.getId(), group.getGroupNumberID())){
            return  "user isn't a member of the group";
        }

        UpdateDB.removeMemberFromGroup(newMember.getId(), group);
        return "successfully removed";
    }

    public String handleBanMember(String memberID, Group group, int adminID) {
        if(adminID != group.getGroupAdminNumberID()){
            return"you are not the admin of this group";
        }
        User newMember = DBGetter.findUserByUserID(memberID);
        if(newMember == null){
            return"the member id doesn't belong to any user";
        }
        // check whether user is a member of the group
        if(!DBGetter.checkMembership(newMember.getId(), group.getGroupNumberID())){
            return"user isn't a member of the group";
        }
        // check whether user is already banned
        if(DBGetter.banCheck(group.getGroupNumberID(),newMember.getId())){
            return "user is already banned from messaging.";
        }

        UpdateDB.banMemberInGroup(newMember.getId(), group);
        return "successfully banned";
    }

    public String  handleUnbanMember(String memberID, Group group, int adminID) {
        if(adminID != group.getGroupAdminNumberID()){
            return "you are not the admin of this group" ;
        }
        User newMember = DBGetter.findUserByUserID(memberID);
        if(newMember == null){
            return "the member id doesn't belong to any user";
        }
        // check whether user is a member of the group
        if(!DBGetter.checkMembership(newMember.getId(), group.getGroupNumberID())){
            return "user isn't a member of the group";
        }
        // check whether user is already banned
        if(!DBGetter.banCheck(group.getGroupNumberID(),newMember.getId())){
            return "user is already unbanned";
        }
        UpdateDB.unbanMemberInGroup(newMember.getId(), group);

        return "successfully unbanned";
    }

    public void handleLeaveGroup(Group group, int memberNumberID) {
        UpdateDB.removeMemberFromGroup( memberNumberID, group);
    }
}
