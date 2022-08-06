package project.database;

import project.models.Group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import static project.database.DBInfo.getConnection;

public class UpdateDB {

    public static void messageCreationInPrivateChat(String message, int userID, int friendID, Date creationDate, int forwardedFromID, int repliedToID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            if(forwardedFromID == -1 && repliedToID == -1 )
                statement.execute("INSERT INTO private_message( sender_id, receiver_id, text, creation_time, is_replied)  VALUES( "+userID+","+friendID+",'"+message+"','"+now.format(dtf)+"', FALSE)");
            else
                statement.execute("INSERT INTO group_message( sender_id, group_id, text, creation_time, forwarded_from, replied_to, is_replied)  VALUES( "+userID+","+friendID+",'"+message+"','"+now.format(dtf)+"',"+forwardedFromID+","+repliedToID+", TRUE)");
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void deletePrivateChat(int id1, int id2) {
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM TABLE private_chat WHERE first_user_id_1 =" + id1 +"AND second_user_id ="+ id2 );
            statement.execute("DELETE FROM TABLE private_chat WHERE first_user_id_1 =" + id2 +"AND second_user_id ="+ id1 );
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void createPrivateChat(int firstID, int secondID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try{
            Connection con = getConnection();
            String query = "INSERT INTO private_chat(first_user_id, second_user_id) VALUES( " + firstID +", "+secondID+" )";
            con.createStatement().execute(query);

            con.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void banMemberInGroup(int memberID, Group group) {
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            statement.execute("INSERT INTO ban_list VALUES( " + group.getGroupNumberID() + ", " + memberID + ")");
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void unbanMemberInGroup(int memberID, Group group) {
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            int var10001 = group.getGroupNumberID();
            statement.execute("DELETE FROM ban_list WHERE group_id = " + var10001 + " AND user_id =" + memberID);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }


    public static void removeMemberFromGroup(int memberID, Group group) {
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            int var10001 = group.getGroupNumberID();
            statement.execute("DELETE FROM membership WHERE group_number_id =" + var10001 + " AND user_number_id =" + memberID);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }


    public static void addMemberToGroup(int groupNumberID, int memberID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO membership(group_number_id, user_number_id, join_date) VALUES( " + groupNumberID + ", " + memberID + ", '" + now.format(dtf) + "')");
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }


    public static void createGroup(String groupID, String groupName, int adminID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime var4 = LocalDateTime.now();

        try {
            Connection con = DBInfo.getConnection();
            String query1 = "INSERT INTO `group`(group_id, group_name, group_admin_id) VALUES( '" + groupID + "', '" + groupName + "', " + adminID + ")";
            con.createStatement().execute(query1);
            int groupNumberID = ((Group)Objects.requireNonNull(DBGetter.findGroupByGroupID(groupID))).getGroupNumberID();
            String query2 = "INSERT INTO membership(group_number_id, user_number_id) VALUES( " + groupNumberID + ", " + adminID + " )";
            con.createStatement().execute(query2);
            con.close();
        } catch (Exception var9) {
            var9.printStackTrace();
        }
    }

    public static void deleteGroup(int groupNumberID) {
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM `group` WHERE group_number_id = " + groupNumberID);
            statement.execute("DELETE FROM membership WHERE group_number_id = " + groupNumberID);
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void changeGroupName(int groupNumberID, String newName) {
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("UPDATE `group` SET group_name = '" + newName + "' WHERE group_number_id = " + groupNumberID + " ;");
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public static void changeGroupID(int groupNumberID, String newGroupID) {
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("UPDATE `group` SET group_id = '" + newGroupID + "' WHERE group_number_id = " + groupNumberID);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }


    public static void editGroupMessageTextInDatabase(String editedText, int messageID) {
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            statement.execute("UPDATE  group_message SET `text` = " + editedText + " WHERE message_id = " + messageID);
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void messageCreationInGroup(String message, int senderID, int groupID, Date creationDate, int forwardedFromID, int repliedToID){

        // have to include time of sending the message too
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try{
            Connection con = getConnection();
            if(forwardedFromID == -1 && repliedToID == -1 )

                con.createStatement().execute("INSERT INTO group_message( sender_id, group_id, text, creation_time, is_replied)  VALUES( "+senderID+" , "+groupID+" , '"+message+"', '"+now.format(dtf)+"' , FALSE)");
            else
                con.createStatement().execute("INSERT INTO group_message( sender_id, group_id, text, creation_time, forwarded_from, replied_to, is_replied)  VALUES( "+senderID+","+groupID+","+message+","+senderID+",'"+now.format(dtf)+"',"+groupID+","+forwardedFromID+","+repliedToID +", true)");

            con.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void blockerBlocks(int blocker, int blocked) {
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            //         statement.executeQuery("INSERT INTO block_list( sender_id, group_id, text, creation_time, is_replied)  VALUES( "+senderID+","+groupID+","+message+","+senderID+","+creationDate+","+groupID+", FALSE)");
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
