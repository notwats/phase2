package project.database;

import project.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static project.database.DBInfo.getConnection;

public class UserDB {

    public static void addUser(User user) {
        try {
            if (user == null)
                return;
            Connection con = getConnection();
            String query = "insert into `user` values(NULL,'" + user.getUserID() + "'" +
                    ",'" + user.getUsername() + "','" + user.getPassword() + "','" +
                    user.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                    "','" + user.getSecurityAnswer() + "'," + user.getSecurityQuestion() + ","
                    + ((user.getIsNormal()) ? "1" : "0") +",NULL)";
            // System.out.println(query);
            con.createStatement().execute(query);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void updateUser(User user) {
        try {
            Connection con = getConnection();
            String query = "update `user` set user_id = '" + user.getUserID() +
                    "', username = '"+ user.getUsername()+
                    "', password = '" + user.getPassword()+"','"+user.getProfileImage() +
//                    "', type = " +
//                    ((user.getIsNormal()) ? "1" : "0")+
                    "';";
            // System.out.println(query);
            con.createStatement().execute(query);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(User user) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            st.execute("delete from `user` where user_id = '" + user.getUserID()+"';");
            st.execute("delete from `post` where sender_id = '" + user.getUserID()+"';");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getFollowings(Integer userID){
        ArrayList<Integer> following= new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from followship where is_following_id = " + userID + ";" ;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                following.add(rs.getInt(2));
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return following;


    }


    public static ArrayList<Integer> getFollowers(Integer userID){
        ArrayList<Integer> followers= new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from followship where is_followed_id = " + userID + ";" ;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                followers.add(rs.getInt(1));
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followers;
    }

    public static void unFollow(User loggedInUser, User currentProfile) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            //st.execute("delete from `followship` where user_id = '" + user.getUserID()+"';");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void follow(User loggedInUser, User currentProfile) {

    }
}
