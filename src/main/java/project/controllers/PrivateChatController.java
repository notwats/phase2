package project.controllers;

import project.database.DBGetter;
import project.database.UpdateDB;
import project.models.User;

import java.util.Date;

public class PrivateChatController {

    public boolean handleSendMessage(String message, int userID, int friendID, Date dateOfNow, int forwardedFrom, int inReplyTo){
        if(message.length() >= 50){
            System.out.println("your message is too long");
            return true;
        }
        // check whether the user is blocked by the friend
        if(DBGetter.BlockedByBLocker(userID, friendID)){
            System.out.println("unfortunately you are blocked by the user so you can't send them messages");
            return false;
        }

        UpdateDB.messageCreationInPrivateChat(message, userID, friendID, dateOfNow, forwardedFrom, inReplyTo);

        return true;
    }

    public String handleBlockUser(int ID, String userID) {
        User newMember = DBGetter.findUserByUserID(userID);
        if(newMember == null){
           return "the member id doesn't belong to any user";

        }
        // check whether the member isn't already in the chat
        if(DBGetter.BlockedByBLocker(newMember.getId(), ID)){
            return "user is already blocked";
        }

        UpdateDB.blockerBlocks(ID, newMember.getId());
        return "successfully blocked";
    }

    public String handleUnblockUser(int id, String userID) {
        User newMember = DBGetter.findUserByUserID(userID);
        if(newMember == null){
            return "the member id doesn't belong to any user";

        }
        // check whether the member isn't already in the chat
        if(!DBGetter.BlockedByBLocker(newMember.getId(), id)){
            return "user isn't blocked";
        }

        UpdateDB.unblockerUnblocks(id, newMember.getId());
        return "successfully unblocked";
    }
}
