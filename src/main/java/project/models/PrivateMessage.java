package project.models;

import project.database.DBGetter;
import project.database.UpdateDB;

import java.util.Date;

public class PrivateMessage {
    public final int messageID;
    public int senderID;
    public int receiverID;
    String messageText;
    public int inReplyTo;
    public Date date;
    public int forwardedFrom;

    public PrivateMessage(int firstUserID, int secondUserID, int messageID, String messageText, int inReplyTo, Date date, int forwardedFrom) {
        this.senderID = firstUserID;
        this.messageID = messageID;
        this.receiverID = secondUserID;
        this.messageText = messageText;
        this.inReplyTo = inReplyTo;
        this.date = date;
        this.forwardedFrom = forwardedFrom;
    }


    public String getMessageText() {
        return messageText;
    }

    public void editGroupMessage(String editedText ){
        messageText = editedText;
        UpdateDB.editGroupMessageTextInDatabase(editedText, messageID);
    }
    //-----
    public String copy(){return"";}

    public void edit(){}

    public void show(int senderID) {
        User user = DBGetter.findUserByUserNumberID(senderID);
        if (inReplyTo == -1) {
            System.out.println(user.username + " : " + messageText);
        } else{
            GroupMessage repMessage = DBGetter.findMessageByMessageID(inReplyTo);
            if(repMessage != null)
                System.out.println(user.username + " : " + messageText + " in rep to " + repMessage.getMessageText().substring(0, 10));
            else
                System.out.println(user.username + " : " + messageText + " in rep to deleted message ");
        }
    }
}
