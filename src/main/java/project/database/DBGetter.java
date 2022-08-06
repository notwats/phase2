package project.database;
// line 118


import project.SceneController;
import project.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static project.database.DBInfo.getConnection;

public class DBGetter {

//    public static void connecting() {
//        try{
//            DBInfo connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb", "root", "");
//
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user`");
//
//            while(resultSet.next()){
//                System.out.println(resultSet.getString("username"));
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void showPrivateChats(ArrayList<String> chats) {
        //\
    }
    public static User findUserByUserNumberID(int senderID) {
        User user = null;
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user` WHERE user_number_id = " + senderID + ";\n");
            if (!resultSet.next()) {
                return null;
            }

                user = new User( senderID ,resultSet.getString("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("security_answer"),
                        resultSet.getInt("security_num") , resultSet.getInt("type"));

                user.setFollowersID(UserDB.getFollowers(user.getNumberID()));
                user.setFollowingsID(UserDB.getFollowings(user.getNumberID()));
                user.setPosts(PostDB.getPostByUserID(user.getNumberID()));

user.setProfileImage(resultSet.getString("profile_image"));
                // other businesss

            user.setId(senderID);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }


    public static User findUserByUserID(String userID) {
        User user = null;
        try {
            Connection connection = DBInfo.getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user` WHERE user_id= '" + userID + "';\n");
            if (!resultSet.next()) {
                return null;
            }

            user = new User( resultSet.getInt("user_number_id") ,resultSet.getString("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("security_answer"),
                    resultSet.getInt("security_num") , resultSet.getInt("type"));

            user.setFollowersID(UserDB.getFollowers(user.getNumberID()));
                user.setFollowingsID(UserDB.getFollowings(user.getNumberID()));

                user.setPosts(PostDB.getPostByUserID(user.getNumberID()));


            user.setProfileImage(resultSet.getString("profile_image"));
                // other businesss

          //  user.setId(resultSet.getInt("user_number_id"));
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static ArrayList<Group> findGroupsWithMemberID(int memberID) {
        ArrayList<Group> groups = new ArrayList<>();

        try {
            Connection connection = getConnection();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();

            ResultSet resultSet = statement1.executeQuery("SELECT * FROM membership WHERE user_number_id = " + memberID);

            ArrayList<Integer> groupNumberIDs = new ArrayList<>();

            while (resultSet.next()) {
                groupNumberIDs.add(resultSet.getInt("group_number_id"));

            }

            for (Integer groupNumberID:
                    groupNumberIDs) {
                ResultSet groupSet = statement2.executeQuery("SELECT * FROM `group` WHERE group_number_id = " + groupNumberID );
                if(groupSet != null && groupSet.next()) {
                    Group newGroup = new Group();
                    newGroup.setGroupNumberID(groupSet.getInt("group_number_id"));
                    newGroup.setGroupID(groupSet.getString("group_id"));
                    newGroup.setGroupName(groupSet.getString("group_name"));
                    newGroup.setGroupAdminID(groupSet.getInt("group_admin_id"));
                    newGroup.setGroupName(groupSet.getString("group_name"));
                    groups.add(newGroup);
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //   System.out.println(groups.size());
        return groups;
    }

    public static ArrayList<GroupMessage> findGroupMessagesByGroupID(int groupID) {
        ArrayList<GroupMessage> messages = new ArrayList<>();

        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM group_message WHERE group_id = " + groupID + " ORDER BY creation_time");

            while(resultSet.next()) {
                int senderID = Integer.parseInt(resultSet.getString("sender_id"));
                int messageID = Integer.parseInt(resultSet.getString("message_id"));
                int inReplyTo;
                if (resultSet.getString("replied_to") != null) {
                    inReplyTo = Integer.parseInt(resultSet.getString("replied_to"));
                } else {
                    inReplyTo = -1;
                }

                int forwardedFrom;
                if (resultSet.getString("forwarded_from") != null) {
                    forwardedFrom = Integer.parseInt(resultSet.getString("forwarded_from"));
                } else {
                    forwardedFrom = -1;
                }

                Date creationDate = resultSet.getDate("creation_time");
                GroupMessage newMessage = new GroupMessage(groupID, senderID, messageID, resultSet.getString("text"), inReplyTo, creationDate, forwardedFrom);
                messages.add(newMessage);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return messages;
    }


    public static GroupMessage findMessageByMessageID(int messageID) {
        GroupMessage newMessage = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM group_message WHERE message_id = " + messageID);

            while (resultSet.next()) {
                int senderID = Integer.parseInt(resultSet.getString("sender_id"));
                int groupID = Integer.parseInt(resultSet.getString("group_id"));
                int inReplyTo = Integer.parseInt(resultSet.getString("replied_message_id"));
                int forwardedFrom = Integer.parseInt(resultSet.getString("forwarded_from"));
                Date creationDate = resultSet.getDate("creation_date");
                newMessage = new GroupMessage(groupID, senderID, messageID, resultSet.getString("text"), inReplyTo, creationDate, forwardedFrom);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newMessage;
    }

    public static Group findGroupByGroupNumberID(int groupNumberID) {

        Group group = null;
        try {
            Connection connection =getConnection();
            Statement statement = connection.createStatement();

            ResultSet groupSet = statement.executeQuery("SELECT * FROM `group` WHERE group_number_id = " + groupNumberID);

            if(groupSet != null && groupSet.next()) {
                group = new Group();
                group.setGroupNumberID(groupNumberID);
                group.setGroupID(groupSet.getString("group_id"));
                group.setGroupName(groupSet.getString("group_name"));
                group.setGroupAdminID(groupSet.getInt("group_admin_id"));
                group.setGroupName(groupSet.getString("group_name"));
            } else{
                return null;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return group;
    }

    public static Group findGroupByGroupID(String groupID) {
        Group group;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet groupSet = statement.executeQuery("SELECT * FROM `group` WHERE group_id = '" + groupID+"'") ;

            group = new Group();
            if(groupSet != null && groupSet.next()) {
                group.setGroupID(groupID);
                group.setGroupName(groupSet.getString("group_name"));
                group.setGroupAdminID(Integer.parseInt(groupSet.getString("group_admin_id")));
                group.setGroupNumberID(groupSet.getInt("group_number_id"));
            } else{
                return null;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return group;
    }

    public static boolean banCheck(int groupNumberID, int senderID) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet groupSet = statement.executeQuery("SELECT * FROM ban_list WHERE group_id = " + groupNumberID + " AND user_id = " + senderID);

            if (groupSet != null && groupSet.next())
                return true;

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean BlockedByBLocker(int blockedID, int blocker) {
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            ResultSet groupSet = statement.executeQuery("SELECT * FROM block_list WHERE blocked_id = " + blockedID + " AND blocked_by_id = " + blocker);
            if (groupSet != null && groupSet.next()) {
                return true;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return false;
    }

    public static boolean checkMembership(int memberID, int groupNumberID) {

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet groupSet = statement.executeQuery("SELECT * FROM membership WHERE group_number_id = " + groupNumberID + " AND user_number_id = " + memberID);

            if (groupSet != null && groupSet.next())
                return true;

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPrivateChat(int firstID, int secondID) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet privateSet1 = statement.executeQuery("SELECT * FROM private_chat WHERE first_user_id = " + firstID + " AND second_user_id = " + secondID);
            if(privateSet1.next())
                return true;
            ResultSet privateSet2 = statement.executeQuery("SELECT * FROM private_chat WHERE first_user_id = " + secondID + " AND second_user_id = " + firstID);
            if(privateSet2.next())
                return true;

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<Personal> findChatsWithMemberID(int numberID) {
        ArrayList<Personal> chats = new ArrayList<>();

        try {
            Connection connection = getConnection();
            Statement statement1 = connection.createStatement();

            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM private_chat WHERE first_user_id = " + numberID + " OR second_user_id = " + numberID );

            while (resultSet1.next()) {
                int user1id = resultSet1.getInt("first_user_id");
                int user2id = resultSet1.getInt("second_user_id");
                Personal personal = new Personal(user1id, user2id, resultSet1.getString("creation_date"));
                chats.add(personal);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chats;
    }

    public static ArrayList<PrivateMessage> findPrivateMessagesWithBothMembersID(int id1, int id2) {
        ArrayList<PrivateMessage> messages = new ArrayList<>();

        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM private_message WHERE sender_id = " + id1 + " AND receiver_id = " + id2 + " ORDER BY creation_time");

            int senderID;
            int receiverID;
            int messageID;
            while(resultSet.next()) {
                senderID = Integer.parseInt(resultSet.getString("sender_id"));
                receiverID = Integer.parseInt(resultSet.getString("receiver_id"));
                messageID = Integer.parseInt(resultSet.getString("message_id"));
                int inReplyTo;
                if (resultSet.getString("replied_to") != null) {
                    inReplyTo = Integer.parseInt(resultSet.getString("replied_to"));
                } else {
                    inReplyTo  = -1;
                }

                int forwardedFrom;
                if (resultSet.getString("replied_to") != null) {
                    forwardedFrom = Integer.parseInt(resultSet.getString("forwarded_from"));
                } else {
                    forwardedFrom = -1;
                }

                Date creationDate = resultSet.getDate("creation_time");
                PrivateMessage newMessage = new PrivateMessage(senderID, receiverID, messageID, resultSet.getString("text"), inReplyTo , creationDate, forwardedFrom);
                messages.add(newMessage);
            }

            Statement statement1 = connection.createStatement();
            ResultSet resultSet2 = statement1.executeQuery("SELECT * FROM private_message WHERE sender_id = " + id2 + " AND receiver_id = " + id1 + " ORDER BY creation_time");

            while(resultSet2.next()) {
                senderID = Integer.parseInt(resultSet2.getString("sender_id"));
                receiverID = Integer.parseInt(resultSet2.getString("receiver_id"));
                messageID = Integer.parseInt(resultSet2.getString("message_id"));
                int inReplyTo;
                if (resultSet2.getString("replied_to") != null) {
                    inReplyTo = Integer.parseInt(resultSet2.getString("replied_to"));
                } else {
                    inReplyTo = -1;
                }

                int forwardedFrom;
                if (resultSet2.getString("replied_to") != null) {
                    forwardedFrom = Integer.parseInt(resultSet2.getString("forwarded_from"));
                } else {
                    forwardedFrom = -1;
                }

                Date creationDate = resultSet2.getDate("creation_time");
                PrivateMessage newMessage = new PrivateMessage(senderID, receiverID, messageID, resultSet2.getString("text"), inReplyTo, creationDate, forwardedFrom);
                messages.add(newMessage);
            }

            return messages;
        } catch (Exception var15) {
            var15.printStackTrace();
            return messages;
        }
    }

}
