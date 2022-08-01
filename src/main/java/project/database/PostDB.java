package project.database;

import project.models.Comment;
import project.models.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostDB extends DBGetter {

    public static ArrayList<Post> getPostByUserID(Integer sender_id)  {
        ArrayList<Post> ret = new ArrayList<>();
        try {
            Connection con = DBInfo.getConnection();
            Statement st = con.createStatement();
            String query = "select * form post where sender_id = " + sender_id;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) { // each post
                Post ps = new Post();
                ps.setPostID(rs.getInt("post_id"));
                ps.setSender(DBGetter.findUserByUserNumberID(rs.getInt(2)));
                //  ps.setRepliedPost(getPostbyPostID(rs.getLong(5)));
                //     ps.setLikes(rs.getInt(6));
                //    ps.setViews(rs.getInt(7));
                //    ps.setComments(rs.getInt(8));
                ps.setComments(getCommentByPostID(ps.getPostID()));
                ps.setCreationDate(LocalDateTime.parse(rs.getString("creation_time")));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static ArrayList<Comment> getCommentByPostID(Integer post_id) throws SQLException {
        ArrayList<Comment> ret = new ArrayList<>();
        Connection con = DBInfo.getConnection();
        Statement st = con.createStatement();
        String query = "select * form comment where post_id = " + post_id;
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Comment cc = new Comment();
            cc.setCommentID(rs.getInt(1));
            cc.setPost(getPostByPostID(post_id));
            cc.setSender(findUserByUserNumberID(rs.getInt(3)));
            cc.setLikeNumber(rs.getInt(4));
            cc.setRepliedTo(getCommentByCommentID(rs.getInt(5)));
            cc.setCommentText(rs.getString(6));
            ret.add(cc);
        }
        return ret;
    }

    private static Comment getCommentByCommentID(int commentID) throws SQLException {
        Comment ret = null;
        Connection con = DBInfo.getConnection();
        Statement st = con.createStatement();
        String query = "select * form comment where comment_id = " + commentID;
        ResultSet rs = st.executeQuery(query);


        return ret;
    }


    public static Post getPostByPostID(Integer post_id) {
        Post post = null;
        try {
            Connection connection = DBInfo.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM post WHERE post_id = " + post_id);
//        if (!rs.next()) {
//            return null;
//        }
            post.setPostID(post_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void deletePost(Post post) throws SQLException {
        Connection con = DBInfo.getConnection();
        Statement st = con.createStatement();
        st.execute("delete from post where post_id = " + post.getPostID());
        st.execute("delete from comment where post_id = " + post.getPostID());
        st.close();
        con.close();
    }

    public static void updatePost(Post post) throws SQLException {
        Connection con = DBInfo.getConnection();
        Statement st = con.createStatement();
//        st.execute("update post set post_data = '" + post.getText().toString() + "', post_likes_count = " +
//                "" + Integer.toString(post.getLikes()) + ", post_view_count = " + post.getViews()
//                + ", post_comments_count = "
//                + post.getComments() + " where post_id = " + post.getId() + ";");
//        con.close();
    }

    public static void addPost(Post post) throws SQLException {
        Connection con = DBInfo.getConnection();
        Statement st = con.createStatement();
//        st.execute("insert into post values(NULL," +
//                "'" + post.getText().toString() + "','" +
//                "" + post.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "', " +
//                "" + post.getSender().getId() + ", "
//                + (post.getRepliedPost() != null ? Long.toString(post.getRepliedPost().getId()) : "0") + ", 0, 0, 0)");
//        con.close();
    }
}
