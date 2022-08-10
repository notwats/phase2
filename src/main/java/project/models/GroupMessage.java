package project.models;

import lombok.Getter;
import lombok.Setter;
import project.database.DBGetter;
import project.database.PostDB;
import project.database.UpdateDB;

import java.util.Date;
@Getter
@Setter
public class GroupMessage {
    public int groupNumberID;
    int receiverID;
    int senderID;
     public final int messageID;
    String messageText;
    public int inReplyTo =-1 ;
    public Date date;
    public int forwardedFrom;

    public GroupMessage(int chatID, int senderID, int messageID, String messageText, int inReplyTo, Date date, int forwardedFrom) {
        this.groupNumberID = chatID;
        this.messageID = messageID;
        this.senderID = senderID;
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

    public void show() {
        User user = DBGetter.findUserByUserNumberID(senderID);
        if (inReplyTo == -1) {
            System.out.println(user.username + " : " + messageText);
        } else{
            GroupMessage repMessage = DBGetter.findMessageByMessageID(inReplyTo);
            if(repMessage != null )
                System.out.println(user.username + " : " + messageText + " in rep to " + repMessage.getMessageText().substring(0, 10));
            else
                System.out.println(user.username + " : " + messageText + " in rep to deleted message ");
        }
    }
        //graphic function

    public void forward(){}

    public void report(){}

    public void reply(){}

    public void copyLink(){}

    public void delete(){}

    public void reaction(){}

    //public void pin(){}


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (forwardedFrom!=-1 && DBGetter.findMessageByMessageID(forwardedFrom)!=null){
            ret.append("forwarded from " + DBGetter.findMessageByMessageID(forwardedFrom).getMessageText() + " <--    " + "\n");
        }
        if ( inReplyTo!=-1 && DBGetter.findMessageByMessageID(inReplyTo)!=null) {
            ret.append("in replied to " + DBGetter.findMessageByMessageID(inReplyTo).getMessageText() + " -->     " +"\n");
        }
        ret.append(DBGetter.findUserByUserNumberID(this.senderID).getUsername() + ": ");
        ret.append(messageText );
        //  ret.append(likeNumber + " user like this comment");

        return ret.toString();
    }
}
