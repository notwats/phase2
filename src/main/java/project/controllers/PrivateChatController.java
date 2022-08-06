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

    public void handleBlockUser(int ID, String userID) {
        User newMember = DBGetter.findUserByUserID(userID);
        if(newMember == null){
            System.out.println("the member id doesn't belong to any user");
            return;
        }
        // check whether the member isn't already in the chat
        if(DBGetter.BlockedByBLocker(newMember.getId(), ID)){
            System.out.println("user is already blocked");
            return;
        }

        UpdateDB.blockerBlocks(ID, newMember.getId());
    }
}
