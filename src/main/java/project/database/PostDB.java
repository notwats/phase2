package project.database;

// add post creation date not sure

import project.database.DBGetter;
import project.database.UserDB;
import project.models.Comment;
import project.models.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PostDB extends DBGetter {

    public static Post getPostByPostID(Integer post_id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Post ps = new Post();
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM post WHERE post_id = " + post_id);
            if (!rs.next()) {
                return null;
            }
            ps.setPostID(rs.getInt("post_id"));
            ps.setSenderid(rs.getInt(2));
            //  ps.setRepliedPost(getPostbyPostID(rs.getLong(5)));
            ps.setContext(rs.getString(3));
            ps.setIsNormal(rs.getBoolean(5));

            ps.setLikedUsersid(getLikedUsersID(ps.getPostID()));
            ps.setLikesDate(getLikesDate(ps.getPostID()));
            ps.setCommentsid(getCommentsIDByPostID(ps.getPostID()));
            ps.setViewsDate(getViewsDate(ps.getPostID()));
            ps.setImageAddress(rs.getString("media"));

            ps.setCreationDate(LocalDate.from(LocalDateTime.parse(rs.getString("creation_time"), formatter)));
            System.out.println(ps.getCreationDate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static void addPost(Post post) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            st.execute("INSERT INTO post( sender_id, text, creation_time, type , media )  VALUES( "
                    + post.getSenderid() + ",'" + post.getContext()
                    + "','" + now.format(dtf) + "'," + ((post.getIsNormal()) ? "1" : "0") +",'"+post.getImageAddress() +"')");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> getFollowingsPost(Integer userID) {
        ArrayList<Post> ret = new ArrayList<>();
        ArrayList<Integer> following = UserDB.getFollowings(userID);
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            for (Integer uuID : following) {
                ArrayList<Post> usersPosts = getPostByUserID(uuID);
                ret.addAll(usersPosts);
            }
            ret.addAll(getPostByUserID(userID));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(ret);

        return ret;
    }



    public static ArrayList<Post> getPostByUserID(Integer sender_id) {
        ArrayList<Post> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from post where sender_id = " + sender_id + ";";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) { // each post
                Post ps = getPostByPostID(rs.getInt("post_id"));


                ret.add(ps);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static ArrayList<Integer> getCommentsIDByPostID(Integer post_id) {
        ArrayList<Integer> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from comment where post_id = " + post_id;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ret.add(rs.getInt(1));

            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Comment getCommentByCommentID(int commentID) {
        Comment cc = new Comment();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from comment where comment_id = " + commentID;
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) {
                return null;
            }
            cc.setCommentID(rs.getInt(1));
            cc.setPostID(rs.getInt(2));
            cc.setSender(rs.getInt(3));
            cc.setLikeNumber(rs.getInt(4));
            cc.setRepliedTo(rs.getInt(5));
            cc.setCommentText(rs.getString(6));

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cc;
    }


    public static void deletePost(Integer postid) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            st.execute("delete from post where post_id = " + postid);
            st.execute("delete from comment where post_id = " + postid);
            st.execute("delete from post_reaction where post_id = " + postid);
            st.execute("delete from post_view where post_id = " + postid);
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePost(Post post) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            // post table
            st.execute("update post set `text` = '" + post.getContext() + "' where post_id = " + post.getPostID() + ";");

            //comment table
            // post reaction table
//another method
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addComment(Comment comment) {
        try {
            Connection con = DBInfo.getConnection();
            Statement statement = con.createStatement();
            // post table

            statement.execute("INSERT INTO comment( sender_id, post_id, text ,replied_comment_id )  VALUES( " + comment.getSender() + "," + comment.getPostID() + ",'" + comment.getCommentText() + "'," + comment.getRepliedTo() + ")");

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateComment(Comment comment) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            // post table
            st.execute("update comment set `text` = '" + comment.getCommentText() + "' where comment_id = " + comment.getCommentID() + ";");
            st.execute("update comment set `like_num` = '" + comment.getLikeNumber() + "' where comment_id = " + comment.getCommentID() + ";");

            //comment table
            // post reaction table
//another method
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteComment(Integer commentid) {
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            st.execute("delete from comment where comment_id = " + commentid);
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLike(Integer postid, Integer reacterid) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            Connection con = DBInfo.getConnection();
            Statement statement = con.createStatement();

            statement.execute("INSERT INTO post_reaction(post_id, reacter_id , date )  VALUES( " + postid + "," + reacterid + ",'" + now.format(dtf) + "')");

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getLikedUsersID(Integer post_id) {
        ArrayList<Integer> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from post_reaction where post_id = " + post_id;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ret.add(rs.getInt(2));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static ArrayList<LocalDate> getLikesDate(Integer post_id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<LocalDate> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from post_reaction where post_id = " + post_id;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ret.add(LocalDate.from(LocalDateTime.parse(rs.getString(3), formatter)));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // ad post...
    public static void newView(Integer postid) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            Connection con = DBInfo.getConnection();
            Statement statement = con.createStatement();

            statement.execute("INSERT INTO post_view(post_id , view_date )  VALUES( " + postid + ",'" + now.format(dtf) + "')");

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<LocalDate> getViewsDate(Integer post_id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<LocalDate> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * from post_view where post_id = " + post_id;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ret.add(LocalDate.from(LocalDateTime.parse(rs.getString(2), formatter)));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
